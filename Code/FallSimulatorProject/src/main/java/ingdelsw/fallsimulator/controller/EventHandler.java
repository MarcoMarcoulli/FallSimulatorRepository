package ingdelsw.fallsimulator.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import ingdelsw.fallsimulator.UI.CurveVisualizer;
import ingdelsw.fallsimulator.UI.Layout;
import ingdelsw.fallsimulator.UI.PlanetIcon;
import ingdelsw.fallsimulator.math.NonConvergenceException;
import ingdelsw.fallsimulator.math.Point;
import ingdelsw.fallsimulator.math.curves.Circle;
import ingdelsw.fallsimulator.math.curves.CubicSpline;
import ingdelsw.fallsimulator.math.curves.Cycloid;
import ingdelsw.fallsimulator.math.curves.Parabola;
import ingdelsw.fallsimulator.observers.WindowResizingObserver;
import ingdelsw.fallsimulator.simulation.Mass;
import ingdelsw.fallsimulator.simulation.MassArrivalObserver;
import ingdelsw.fallsimulator.simulation.MassIcon;
import ingdelsw.fallsimulator.simulation.SimulationManager;

public class EventHandler implements MassArrivalObserver, WindowResizingObserver{
	
	private static final Logger logger = LogManager.getLogger(EventHandler.class);
	
	public enum UIStates {
		IDLE,
    	WAITING_FOR_START_POINT,
        WAITING_FOR_END_POINT,
        INSERTING_INTERMEDIATE_POINTS;
	}
	
	private UIStates state;
	
	private InputController inputController;
	
    private List<SimulationManager> simulations;
	
    private Layout layout;
    
	private static EventHandler theHandler = null; //singleton instance 
	
	private double g;
	
	private double pixelHeightMm;
	
	private EventHandler() //private constructor
	{
		
		inputController = InputController.getController();
		layout = Layout.getLayout();
		layout.addWindowResizingObserver(this); //EventHandler observes layout
		simulations = new ArrayList<>();
		state = UIStates.IDLE;
		
		//event handling association
        layout.getPointsCanvas().setOnMouseClicked(this::handleMouseClick); //reference method association
        
        //lambda expressions associations
        layout.getBtnCancelInput().setOnAction(e -> handleCancelInputClick());
        layout.getBtnParabola().setOnAction(e -> handleParabolaClick());
        layout.getBtnCycloid().setOnAction(e -> handleCycloidClick());
        layout.getBtnCircumference().setOnAction(e -> handleCircleClick());
        layout.getBtnCubicSpline().setOnAction(e-> handleCubicSplineClick());
        layout.getBtnConvexityUp().setOnAction(e -> handleConvexityClick(1));
        layout.getBtnConvexityDown().setOnAction(e -> handleConvexityClick(-1));
        layout.getBtnConfirmRadius().setOnAction(e -> handleConfirmRadiusClick());
        layout.getBtnStopIntermediatePointsInsertion().setOnAction(e -> handleStopIntermediatePointsInsertionClick());
        layout.getBtnInsertAnotherCurve().setOnAction(e -> handleInsertAnotherCurveClick());
        layout.getBtnStartSimulation().setOnAction(e -> handleStartSimulationClick());
		
		for(MassIcon mass : MassIcon.values())
		{
			layout.getMass(mass).setOnMouseClicked(e -> handleMassSelection(mass, (ImageView) e.getSource()));
		}
		
		for(PlanetIcon planet : PlanetIcon.values())
		{
			layout.getPlanet(planet).setOnMouseClicked(e -> handleGravitySelection(planet));
		}
		
		// get screen
        Screen primaryScreen = Screen.getPrimary();

        // get screen pixel resolution
        double screenHeightPixels = primaryScreen.getBounds().getHeight(); // get screen height in pixel
    
        double scaleY = primaryScreen.getOutputScaleY();//get scale factor

        // get the DPI of the screen (dots(pixel) per inch)
        double dpi = 96 * scaleY; // JavaFX uses 96 DPI 

        // Calcola screen height in Mm (25.4 ratio Mm/inch)
        double screenHeightMm = screenHeightPixels * 25.4 / dpi;

        //dimension of a pixel in Mm
        pixelHeightMm = screenHeightMm / screenHeightPixels;

	}
	
	//singleton get method
	public static EventHandler getHandler()
    {
    	if(theHandler == null)
    		theHandler = new EventHandler();
    	return theHandler;
    }
	
	public Layout getLayout()
	{
		return layout;
	}
	
	public InputController getInputController()
	{
		return inputController;
	}
	
	public UIStates getState()
	{
		return state;
	}
	
	//set g depending on the planet clicked
	public void handleGravitySelection(PlanetIcon planet)
    {
    	double gMm;
    	switch(planet) {
    		case MOON : 
    			gMm = 1620;
    			break;
    		case MARS : 
    			gMm = 3730;
    			break;
    		case EARTH : 
    			gMm = 9810;
    			break;
    		case JUPITER : 
    			gMm = 24790;
    			break;
    		case SUN : 
    			gMm = 274000;
    			break;
    		default: gMm = 0;
    	}
    	g = gMm/(pixelHeightMm*100);
    	logger.info("g : {}", g);
    	
    	//state transition to waiting for start point 
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getStartPointMessage(), layout.getBtnCancelInput());
    	state = UIStates.WAITING_FOR_START_POINT;
    }
	
	//handle clicks on the canvas depending on the current state
	public void handleUIState(Point p)
	{
		GraphicsContext gc = layout.getGC();
		switch (state) {
		//handle start point insertion
	    case WAITING_FOR_START_POINT:
	        inputController.setStartpoint(p);
	        //draw a red circle on the point clicked
	        gc.setFill(Color.RED);
	        gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);  
	        //state transition to waiting for end point
	        layout.getControlPanel().getChildren().clear();
	        layout.getControlPanel().getChildren().addAll(layout.getEndPointMessage(), layout.getBtnCancelInput());
	        state = UIStates.WAITING_FOR_END_POINT;
	        break;
	    //handle end point insertion
	    case WAITING_FOR_END_POINT:
	        try {
	            inputController.setEndpoint(p);
	        } catch (IllegalArgumentException e) {
	        	//handle exception if end point is higher than start point
	            inputController.handleException(e);
	            return;
	        }
	        //draw a blue circle on the point clicked
	        gc.setFill(Color.BLUE);
	        gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);
	        //state transition to waiting for end point
	        layout.getControlPanel().getChildren().clear();
	        layout.getControlPanel().getChildren().addAll(layout.getChooseCurveMessage(), layout.getCurveButtons(), layout.getBtnCancelInput());
	        state = UIStates.IDLE;
	        break;
	    //handle intermediate points insertion
	    case INSERTING_INTERMEDIATE_POINTS:
	    	try {
	            inputController.addIntermediatePoint(p);
	        } catch (IllegalArgumentException e) {
	        	//handle exception if end point is not between start point and endl point
	            inputController.handleException(e);
	            return;
	        }
	    	//draw a random color circle on the point clicked
	        gc.setFill(Color.rgb(randomRed, randomGreen, randomBlue));
	        gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10); 
	        break;
	     default: break;
		}
	}
	
	//mouse click on the canvas handling
    public void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        Point p = new Point(x,y);
        handleUIState(p);
    }
    
    //clear data structures when cancel input is clicked
    public void handleCancelInputClick(){
        layout.clear();
        simulations.clear();
        state = UIStates.IDLE;
    }
    
    //random color components
    private int randomRed;
    private int  randomGreen;
    private int  randomBlue;
    Random random = new Random();
	    
    //cycloid click handling
    public void handleCycloidClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	//create cycloid
    	Cycloid cycloid;
    	try{
    		cycloid = new Cycloid(inputController.getStartPoint(),inputController.getEndPoint());
    	}catch(NonConvergenceException e)
    	{
    		//handle exception in case Rapson-Newton method doesn't converge
    		try {
    			//block thread execution
    			inputController.handleException(e);
                Thread.sleep(6000);
                handleCancelInputClick();
                return;
            } catch (InterruptedException e2) {
                logger.error("Il thread è stato interrotto durante il sonno.");
                Thread.currentThread().interrupt();
                return;
            }
    	}
    
    	cycloid.setRandomColors();//set random color for the cycloid
    	simulations.add(new SimulationManager(cycloid));//add cycloid simulation
    	lastSimulation().addMassArrivalObserver(this);
    	
    	//get cycloid points and color
    	Point[] points = lastSimulation().getPoints();
    	int red = lastSimulation().getCurve().getRed();
    	int green = lastSimulation().getCurve().getGreen();
    	int blue = lastSimulation().getCurve().getBlue();
    	
    	//draw the cycloid on the canvas
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green,  blue);
    	
    	lastSimulation().setSlopes(cycloid.calculateSlopes());//set slopes for each point of the cycloid
    	lastSimulation().calculateTimeParametrization(g);//calculate mass passing times for each point of the cycloid
    	
    	//state transition to choose mass 
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    	layout.getCurveButtons().getChildren().remove(layout.getBtnCycloid());
    }
	    
    //parabola click handling
    public void handleParabolaClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	//create cycloid
    	Parabola parabola = new Parabola(inputController.getStartPoint(),inputController.getEndPoint());
    	parabola.setRandomColors();//set random color for the parabola
    	simulations.add(new SimulationManager(parabola)); //add parabola simulation
    	lastSimulation().addMassArrivalObserver(this);
    	//get parabola points and color
    	Point[] points = lastSimulation().getPoints();
    	int red = lastSimulation().getCurve().getRed();
    	int green = lastSimulation().getCurve().getGreen();
    	int blue = lastSimulation().getCurve().getBlue();
    	
    	//draw the parabola on the canvas
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green, blue);
    	
    	lastSimulation().setSlopes(parabola.calculateSlopes());//set slopes for each point of the parabola
    	lastSimulation().calculateTimeParametrization(g);//calculate mass passing times for each point of the parabola
    	
    	//state transition to choose mass
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    	layout.getCurveButtons().getChildren().remove(layout.getBtnParabola());
    }
    
    //circumference click handling
    public void handleCircleClick()
    {
    	//state transition to choose convexity
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().add(layout.getChooseConvexityMessage());
    	layout.getControlPanel().getChildren().addAll(layout.getConvexityButtons(), layout.getBtnCancelInput());
    }
    
    //circumference convexity click handling 
    public void handleConvexityClick(int convexity)
    {
    	//create initial circumference
    	Circle circumference = new Circle(inputController.getStartPoint(),inputController.getEndPoint(), convexity);
    	circumference.setRandomColors();//set random color for the circumference
    	simulations.add(new SimulationManager(circumference));//add circumference simulation
    	lastSimulation().addMassArrivalObserver(this);    	
    	//get circumference points and color
    	Point[] points = lastSimulation().getPoints();    	
    	int red = lastSimulation().getCurve().getRed();
    	int green = lastSimulation().getCurve().getGreen();
    	int blue = lastSimulation().getCurve().getBlue();
    	
    	//draw the circumference on the canvas
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red, green,  blue);
    	
    	if(convexity==1)//convexity up
    	{
    		//sign handling 
    		double deltaX = inputController.getEndPoint().getX() - inputController.getStartPoint().getX();
    		//set slider
        	double initialRadius = (deltaX/Math.abs(deltaX))*circumference.getR();
        	layout.setRadiusSlider(new Slider(initialRadius, initialRadius*3, initialRadius));
    	}
    	else if(convexity==-1)//convexity down
    	{
    		//set slider
    		Slider slider = new Slider(circumference.getR(), circumference.getR()*3, circumference.getR());      	
        	layout.setRadiusSlider(slider);
    	}
    	
    	//add a listener(handleSliderChange) for slider value
        layout.getRadiusSlider().valueProperty().addListener((observable, oldValue, newValue) -> handleSliderChange(newValue.doubleValue(), convexity));
        
        //state transition to choose radius
        layout.getControlPanel().getChildren().clear();
        layout.getControlPanel().getChildren().addAll(layout.getChooseRadiusMessage(), layout.getRadiusSlider(), layout.getBtnConfirmRadius(), layout.getBtnCancelInput());
    }
    
    //listener for slider value change
    public void handleSliderChange(double radius, int convexity)
    {
    	//clear canvas
    	layout.getCurveCanvas().getGraphicsContext2D().clearRect(0, 0, layout.getCurveCanvas().getWidth(), layout.getCurveCanvas().getHeight());
    	//create circumference
    	Circle circumference = new Circle(inputController.getStartPoint(),inputController.getEndPoint(), convexity, radius);
    	
    	//set colors from initial circumference 
    	circumference.setRed(lastSimulation().getCurve().getRed());
    	circumference.setGreen(lastSimulation().getCurve().getGreen());
    	circumference.setBlue(lastSimulation().getCurve().getBlue());
    	
    	simulations.remove(lastSimulation());//remove initial circumference
    	simulations.add(new SimulationManager(circumference));//add new circumference
    	lastSimulation().addMassArrivalObserver(this);
    	
    	//get circumference points and color
    	Point[] points = lastSimulation().getPoints(); 	
    	int red = lastSimulation().getCurve().getRed();
    	int green = lastSimulation().getCurve().getGreen();
    	int blue = lastSimulation().getCurve().getBlue();
    	
    	//draw the circumference with the new radius value on the canvas
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red, green,  blue);
    	
    	//draw again all the previous curves
    	for (int i = 0; i < simulations.size() - 1; i++) {
    		
    		points = simulations.get(i).getPoints();   		
    		red = simulations.get(i).getCurve().getRed();
    		green = simulations.get(i).getCurve().getGreen();
    		blue = simulations.get(i).getCurve().getBlue();
    		
    	    CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red, green, blue);
    	}
    }
    
    //radius confirmation handling
    public void handleConfirmRadiusClick()
    {	
    	lastSimulation().setSlopes(lastSimulation().getCurve().calculateSlopes()); //set slopes for each point of the selected circumference
    	lastSimulation().calculateTimeParametrization(g); //calculate mass passing times for each point of the selected circumference
    	//state transition to choose mass
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    }
    
    //cubic spline click handling
    public void handleCubicSplineClick()
    {
    	//ask to insert intermediate points
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getIntermediatePointsMessage(), layout.getBtnStopIntermediatePointsInsertion(), layout.getBtnCancelInput());
        //set random colors components (max 230 in order not to have too light colors)
    	randomRed = random.nextInt(230);
        randomGreen = random.nextInt(230);
        randomBlue = random.nextInt(230);
        //state transition to inserting intermediate points
    	state = UIStates.INSERTING_INTERMEDIATE_POINTS;
    }
    
    //stop cubic spline intermediate points insertion click
    public void handleStopIntermediatePointsInsertionClick()
    {
    	//state transition to choose mass
    	state = UIStates.IDLE;
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    	
    	//create cubic spline
    	CubicSpline spline = new CubicSpline(inputController.getStartPoint(),inputController.getEndPoint(), inputController.getIntermediatePoint());
    	spline.setRandomColors();//set random color for the spline
    	inputController.clearIntermediatePoints();//clear intermediate points
    	simulations.add(new SimulationManager(spline));//add spline simulation
    	lastSimulation().addMassArrivalObserver(this);
    	
    	//get spline points and color
    	Point[] points = lastSimulation().getPoints();   	
    	int red = lastSimulation().getCurve().getRed();
    	int green = lastSimulation().getCurve().getGreen();
    	int blue = lastSimulation().getCurve().getBlue();
    	
    	//draw the spline on the canvas
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green,  blue);
    	lastSimulation().setSlopes(spline.calculateSlopes());//set slopes for each point of the spline
    	lastSimulation().calculateTimeParametrization(g);//calculate mass passing times for each point of the spline
    }
    
    //continue inserting curves
    void handleInsertAnotherCurveClick()
    {
    	//state transition to choose curve
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getChooseCurveMessage(), layout.getCurveButtons(), layout.getBtnCancelInput());
    }
    
    // mass choice 
    void handleMassSelection(MassIcon iconType, ImageView selectedMass) {
    	
        ImageView mass = new ImageView(selectedMass.getImage()); //icon representing the mass
        lastSimulation().setMass(new Mass(inputController.getStartPoint(), iconType, mass)); //set mass on the start point
        
        layout.getAnimationPane().getChildren().add(lastSimulation().getMass().getIcon());//add mass to the animation pane
        layout.getControlPanel().getChildren().clear();
        layout.getMassIconButtons().getChildren().remove(selectedMass.getParent()); //remove selected mass from mass buttons list
        
        if(layout.getMassIconButtons().getChildren().isEmpty()){	
        	//if there are no more masse to choose it forces to start simulation
        	layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnCancelInput()); 
        }
        //if there are more masses it's possible to add another curve
        else layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnInsertAnotherCurve(), layout.getBtnCancelInput()); 
    }
 
    private int numberOfSimulations;
    
    //start the fall of masses simulation
    void handleStartSimulationClick()
    {
    	numberOfSimulations = simulations.size();
    	
    	//clear arrival times messages
    	layout.getControlPanel().getChildren().clear(); 
    	layout.getArrivalTimeMessages().clear();
    	layout.getNeverArriveMessages().clear();
    	layout.getMassArrivalMessagesBox().getChildren().clear();
    	//state transition to simulating
    	layout.getControlPanel().getChildren().addAll(layout.getSimulatingMessage(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox());
    	
    	//start all the animations
    	for(int i=0; i<numberOfSimulations; i++)
    	{
    		simulations.get(i).startAnimation();
    	}
    }
    
    //MassArrivalObserver method implementation
    @Override
    //reacts to mass arrival to final point
    public void onMassArrival(SimulationManager source, boolean arrived) {
    	numberOfSimulations--;//decrease the number of simulations running
    	int i = simulations.indexOf(source);//index of the simulation that has arrived
    	
    	if(i>=0)
    	{
    		if(arrived)//if the mass has reached end point
    		{
    			layout.getMassArrivalMessagesBox().getChildren().removeAll(layout.getArrivalTimeMessages());
    			String massName = simulations.get(i).getMass().getIconTypeString(); //get arrived mass name string
    			String arrivalTime = String.format("%.5f", simulations.get(i).getArrivalTime()); //get the time taken to reach end point in string format
    			String curveName = simulations.get(i).getCurve().curveName();//get curve name string
    			layout.getArrivalTimeMessages().add(new Label(massName + " sulla " + curveName + " è arrivato in " + arrivalTime + " secondi.")); //add arrival message for current simulation
    			layout.getArrivalTimeMessages().sort(Comparator.comparingInt(label -> extractNumber(label.getText()))); //order arrival times messages in increasing order respect to arrival times
    			layout.getMassArrivalMessagesBox().getChildren().addAll(0, layout.getArrivalTimeMessages());
    		}
    		//if the mass stopped before end point
    		else {
    			layout.getMassArrivalMessagesBox().getChildren().removeAll(layout.getNeverArriveMessages());
    			String massName = simulations.get(i).getMass().getIconTypeString(); //get arrived mass name string
    			String curveName = simulations.get(i).getCurve().curveName(); //get curve name string
    			layout.getNeverArriveMessages().add(new Label(massName + " sulla " + curveName +" non arriverà mai a destinazione"));//add never arrive message for current simulation
    			layout.getMassArrivalMessagesBox().getChildren().addAll(layout.getNeverArriveMessages());
    		}
    	}
    	
    	//if all the simulations has arrived
    	if(numberOfSimulations == 0)
    	{
    		layout.getControlPanel().getChildren().clear();
    		if(layout.getMassIconButtons().getChildren().isEmpty()) //if there are no more masse to choose it forces to start simulation
            	layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox()); 
    		//if there are more masses it's possible to add another curve
    		else layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnInsertAnotherCurve(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox()); 
    	}
    }
    
    // extract number from text
    static int extractNumber(String text) {
        // remove everinthing except of numbers
        String numberStr = text.replaceAll("[^\\d]", ""); // "\\d" corresponds to numbers , "^" negates the rest
        try {
            return Integer.parseInt(numberStr); // casting from string to int
        } catch (NumberFormatException e) {
            return 0; //default value if there are no numbers
        }
    }
    
    //get last simulation from simulations list
    SimulationManager lastSimulation()
    {
    	return simulations.get(simulations.size()-1);
    }
    
    //WindowResizingObserver method implementation
    @Override 
    public void onWindowResizing()
    {
    	//calls cancel input when window gets resized
    	handleCancelInputClick();
    }
    
    //g getter
	public double getG() {
		return g;
	}
	
	//simulations getter
	public List<SimulationManager> getSimulations() {
		return simulations;
	}
}