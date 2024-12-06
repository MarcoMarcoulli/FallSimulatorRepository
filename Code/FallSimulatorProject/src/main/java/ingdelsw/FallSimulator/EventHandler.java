package ingdelsw.FallSimulator;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import ingdelsw.FallSimulator.Math.Point;
import ingdelsw.FallSimulator.Math.Curves.Circumference;
import ingdelsw.FallSimulator.Math.Curves.CubicSpline;
import ingdelsw.FallSimulator.Math.Curves.Cycloid;
import ingdelsw.FallSimulator.Math.Curves.Parabola;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class EventHandler implements MassArrivalListener, WindowResizingListener{
	
	public enum UIStates {
		SELECTING_GRAVITY,
    	WAITING_FOR_START_POINT,
        WAITING_FOR_END_POINT,
        INSERTING_INTERMEDIATE_POINTS;
	}
	
	private UIStates state;
	
	private InputController inputController;
	
    private ArrayList<SimulationManager> simulations;
	
    private Layout layout;
    
	private static EventHandler theHandler = null;
	
	private static double g;
	
	double pixelHeightMm;
	

	public EventHandler(){
		inputController = InputController.getController();
		layout = Layout.getLayout(this);
		simulations = new ArrayList<>();
		state = UIStates.SELECTING_GRAVITY;
		// Gestione del click sul pannello di disegno
        layout.getPointsCanvas().setOnMouseClicked(e -> handleMouseClick(e, inputController));
        layout.getBtnCancelInput().setOnAction(e -> handleCancelInputClick());
        layout.getBtnParabola().setOnAction(e -> handleParabolaClick());
        layout.getBtnCycloid().setOnAction(e -> handleCycloidClick());
        layout.getBtnCircumference().setOnAction(e -> handleCircumferenceClick());
        layout.getBtnCubicSpline().setOnAction(e-> handleCubicSplineClick());
        layout.getBtnConvexityUp().setOnAction(e -> handleConvexityUpClick());
        layout.getBtnConvexityDown().setOnAction(e -> handleConvexityDownClick());
        layout.getBtnStopIntermediatePointsInsertion().setOnAction(e -> handleStopIntermediatePointsInsertionClick());
        layout.getBtnInsertAnotherCurve().setOnAction(e -> handleInsertAnotherCurveClick());
        layout.getBtnStartSimulation().setOnAction(e -> handleStartSimulationClick());
		layout.getIconViewBernoulli().setOnMouseClicked(e -> handleMassSelection(MassIcon.BERNOULLI, (ImageView) e.getSource()));
		layout.getIconViewGalileo().setOnMouseClicked(e -> handleMassSelection(MassIcon.GALILEO, (ImageView) e.getSource()));
		layout.getIconViewJakob().setOnMouseClicked(e -> handleMassSelection(MassIcon.JAKOB, (ImageView) e.getSource()));
		layout.getIconViewLeibnitz().setOnMouseClicked(e -> handleMassSelection(MassIcon.LEIBNITZ, (ImageView) e.getSource()));
		layout.getIconViewNewton().setOnMouseClicked(e -> handleMassSelection(MassIcon.NEWTON, (ImageView) e.getSource()));
		layout.getIconViewMoon().setOnMouseClicked(e -> handleGravitySelection(PlanetIcon.MOON));
		layout.getIconViewMars().setOnMouseClicked(e -> handleGravitySelection(PlanetIcon.MARS));
		layout.getIconViewEarth().setOnMouseClicked(e -> handleGravitySelection(PlanetIcon.EARTH));
		layout.getIconViewJupiter().setOnMouseClicked(e -> handleGravitySelection(PlanetIcon.JUPITER));
		layout.getIconViewSun().setOnMouseClicked(e -> handleGravitySelection(PlanetIcon.SUN));
		
		 // Ottieni lo schermo primario
        Screen primaryScreen = Screen.getPrimary();

        // Ottieni la risoluzione in pixel
        double screenHeightPixels = primaryScreen.getBounds().getHeight(); // Altezza in pixel

        // Ottieni il fattore di scala HiDPI
        double scaleY = primaryScreen.getOutputScaleY();

        // Ottieni il DPI dello schermo (dots per inch)
        double dpi = 96 * scaleY; // JavaFX usa 96 DPI come base

        // Calcola l'altezza dello schermo in millimetri
        double screenHeightMm = screenHeightPixels * 25.4 / dpi;

        // Calcola la dimensione di un pixel in millimetri
        pixelHeightMm = screenHeightMm / screenHeightPixels;

	}
	
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
	
	public void handleUIState(Point p)
	{
		GraphicsContext gc = layout.getGC();
		switch (state) {
	    case WAITING_FOR_START_POINT:
	        inputController.setStartpoint(p);
	        gc.setFill(Color.RED);
	        gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);  // Cerchio rosso per il punto di partenza
	        layout.getControlPanel().getChildren().clear();
	        layout.getControlPanel().getChildren().addAll(layout.getEndPointMessage(), layout.getBtnCancelInput());
	        state = UIStates.WAITING_FOR_END_POINT;
	        break;
	    case WAITING_FOR_END_POINT:
	        try {
	            inputController.setEndpoint(new Point(p.getX(), p.getY()));
	        } catch (IllegalArgumentException e) {
	            inputController.handleException(e);
	            return;
	        }
	        gc.setFill(Color.BLUE);
	        gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);  // Cerchio blu per il punto di arrivo
	        layout.getControlPanel().getChildren().clear();
	        layout.getControlPanel().getChildren().addAll(layout.getChooseCurveMessage(), layout.getCurveButtons(), layout.getBtnCancelInput());
	        break;
	    case INSERTING_INTERMEDIATE_POINTS:
	    	try {
	            inputController.addIntermediatePoint(new Point(p.getX(),p.getY()));
	        } catch (IllegalArgumentException e) {
	            inputController.handleException(e);
	            return;
	        }
	        gc.setFill(Color.rgb(randomRed, randomGreen, randomBlue));
	        gc.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);  // Cerchio verde per il punto intermedio
	        break;
		}
	}
	
	// Gestione dei click per selezionare il punto di partenza
    public void handleMouseClick(MouseEvent event, InputController inputController) {
        double x = event.getX();
        double y = event.getY();
        Point p = new Point(x,y);
        handleUIState(p);
    }
    
    public void handleCancelInputClick(){
        layout.clear();
        simulations.clear();
        state = UIStates.SELECTING_GRAVITY;
    }
    
    private int randomRed;
    private int  randomGreen;
    private int  randomBlue;
    Random random = new Random();
	 
	//gestore del click sul pulsante CubicSpline
    public void handleCubicSplineClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getIntermediatePointsMessage(), layout.getBtnStopIntermediatePointsInsertion(), layout.getBtnCancelInput());
        randomRed = random.nextInt(230);
        randomGreen = random.nextInt(230);
        randomBlue = random.nextInt(230);
    	state = UIStates.INSERTING_INTERMEDIATE_POINTS;
    }
	    
    //gestore del click sul pulsante Cycloid
    public void handleCycloidClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	Cycloid cycloid = new Cycloid(inputController.getStartPoint(),inputController.getEndPoint());
    	cycloid.setRandomColors();
    	simulations.add(new SimulationManager(cycloid, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green,  blue);
    	simulations.getLast().setSlopes(cycloid.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization(g);
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    	layout.getCurveButtons().getChildren().remove(layout.getBtnCycloid());
    }
	    
    //gestore del click sul pulsante Parabola
    public void handleParabolaClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	Parabola parabola = new Parabola(inputController.getStartPoint(),inputController.getEndPoint());
    	parabola.setRandomColors();
    	simulations.add(new SimulationManager(parabola, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green, blue);
    	
    	simulations.getLast().setSlopes(parabola.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization(g);
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    	layout.getCurveButtons().getChildren().remove(layout.getBtnParabola());
    }
    
    //gestore del click sul pulsante Circumference
    public void handleCircumferenceClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().add(layout.getChooseConvexityMessage());
    	layout.getControlPanel().getChildren().addAll(layout.getConvexityButtons(), layout.getBtnCancelInput());
    }
    
    public void handleConvexityUpClick()
    {
    	Circumference circumference = new Circumference(inputController.getStartPoint(),inputController.getEndPoint(), 1);
    	circumference.setRandomColors();
    	
    	simulations.add(new SimulationManager(circumference, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green,  blue);
    	
    	double deltaX = inputController.getEndPoint().getX() - inputController.getStartPoint().getX();
    	double initialRadius = (deltaX/Math.abs(deltaX))*circumference.getR();
    	layout.setRadiusSlider(new Slider(initialRadius, initialRadius*3, initialRadius));
    	// Aggiungi un listener per il valore dello slider e chiama la funzione
        layout.getRadiusSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
            handleSliderChange(newValue.doubleValue(), 1);
        });
        layout.getBtnConfirmRadius().setOnAction(e -> handleConfirmRadiusClick());
        layout.getControlPanel().getChildren().clear();
        layout.getControlPanel().getChildren().addAll(layout.getChooseRadiusMessage(), layout.getRadiusSlider(), layout.getBtnConfirmRadius(), layout.getBtnCancelInput());
    }
    
    public void handleConvexityDownClick()
    {
    	Circumference circumference = new Circumference(inputController.getStartPoint(),inputController.getEndPoint(), -1);
    	circumference.setRandomColors();
    	simulations.add(new SimulationManager(circumference, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red, green,  blue);
    	
    	Slider slider = new Slider(circumference.getR(), circumference.getR()*3, circumference.getR());
    	
    	layout.setRadiusSlider(slider); 
    	// Aggiungi un listener per il valore dello slider e chiama la funzione
        layout.getRadiusSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
            handleSliderChange(newValue.doubleValue(), -1);
        });
        layout.getBtnConfirmRadius().setOnAction(e -> handleConfirmRadiusClick());
        layout.getControlPanel().getChildren().clear();
        layout.getControlPanel().getChildren().addAll(layout.getChooseRadiusMessage(), layout.getRadiusSlider(), layout.getBtnConfirmRadius());
    }
    
    public void handleSliderChange(double radius, int convexity)
    {
    	layout.getCurveCanvas().getGraphicsContext2D().clearRect(0, 0, layout.getCurveCanvas().getWidth(), layout.getCurveCanvas().getHeight());
    	Circumference circumference = new Circumference(inputController.getStartPoint(),inputController.getEndPoint(), convexity, radius);
    	circumference.setRed(simulations.getLast().getCurve().getRed());
    	circumference.setGreen(simulations.getLast().getCurve().getGreen());
    	circumference.setBlue(simulations.getLast().getCurve().getBlue());
    	simulations.removeLast();
    	simulations.add(new SimulationManager(circumference, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red, green,  blue);
    	for (int i = 0; i < simulations.size() - 1; i++) {
    		
    		points = simulations.get(i).getPoints();
    		
    		red = simulations.get(i).getCurve().getRed();
    		green = simulations.get(i).getCurve().getGreen();
    		blue = simulations.get(i).getCurve().getBlue();
    		
    	    CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red, green, blue);
    	}
    }
    
    public void handleConfirmRadiusClick()
    {
    	double[] slopes = simulations.getLast().getCurve().calculateSlopes();
    	simulations.getLast().setSlopes(slopes);
    	simulations.getLast().calculateTimeParametrization(g);
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    }
    
    public void handleStopIntermediatePointsInsertionClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	CubicSpline spline = new CubicSpline(inputController.getStartPoint(),inputController.getEndPoint(), inputController.getIntermediatePoint());
    	spline.setRandomColors();
    	inputController.clearIntermediatePoints();
    	simulations.add(new SimulationManager(spline, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, layout.getCurveCanvas().getGraphicsContext2D(), red,  green,  blue);
    	simulations.getLast().setSlopes(spline.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization(g);
    	layout.getControlPanel().getChildren().addAll(layout.getChooseMassMessage(), layout.getMassIconButtons(), layout.getBtnCancelInput());
    }
    
    private void handleInsertAnotherCurveClick()
    {
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getChooseCurveMessage(), layout.getCurveButtons(), layout.getBtnCancelInput());
    }
    
    // Gestione della selezione della massa
    private void handleMassSelection(MassIcon iconType, ImageView selectedMass) {
        ImageView mass = new ImageView(selectedMass.getImage());
        simulations.getLast().setMass(new Mass(inputController.getStartPoint(), iconType, mass));
        layout.getAnimationPane().getChildren().add(simulations.getLast().getMass().getIcon());
        layout.getControlPanel().getChildren().clear();
        layout.getMassIconButtons().getChildren().remove(selectedMass);
        if(layout.getMassIconButtons().getChildren().isEmpty())
        	layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnCancelInput()); 
        else layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnInsertAnotherCurve(), layout.getBtnCancelInput()); 
    }
 
    private int numberOfSimulations;
    
    @Override
    public void onMassArrival(SimulationManager source, boolean arrived) {
    	numberOfSimulations--;
    	int i = simulations.indexOf(source);
    	if(i>=0)
    	{
    		if(arrived)
    		{
    			layout.getMassArrivalMessagesBox().getChildren().removeAll(layout.getArrivalTimeMessages());
    			String massName = simulations.get(i).getMass().getIconTypeString();
    			double arrive = simulations.get(i).getArrivalTime();
    			String arrivalTime = String.format("%.5f", arrive);
    			String curveName = simulations.get(i).getCurve().curveName();
    			layout.getArrivalTimeMessages().add(new Label(massName + " sulla " + curveName + " è arrivato in " + arrivalTime + " secondi."));
    			layout.getArrivalTimeMessages().sort(Comparator.comparingInt(label -> extractNumber(label.getText())));
    			layout.getMassArrivalMessagesBox().getChildren().addAll(0, layout.getArrivalTimeMessages());
    		}
    		else {
    			layout.getMassArrivalMessagesBox().getChildren().removeAll(layout.getNeverArriveMessages());
    			String massName = simulations.get(i).getMass().getIconTypeString();
    			String curveName = simulations.get(i).getCurve().curveName();
    			layout.getNeverArriveMessages().add(new Label(massName + " sulla " + curveName +" non arriverà mai a destinazione"));
    			layout.getMassArrivalMessagesBox().getChildren().addAll(layout.getNeverArriveMessages());
    		}
    	}
    	
    	if(numberOfSimulations == 0)
    	{
    		layout.getControlPanel().getChildren().clear();
    		if(layout.getMassIconButtons().getChildren().isEmpty())
            	layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox()); 
            else layout.getControlPanel().getChildren().addAll(layout.getBtnStartSimulation(), layout.getBtnInsertAnotherCurve(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox()); 
    	}
    }
    
 // Funzione per estrarre il numero dal testo della Label
    private static int extractNumber(String text) {
        // Rimuove tutto tranne i numeri
        String numberStr = text.replaceAll("[^\\d]", ""); // "\\d" corrisponde a cifre, il caret "^" nega tutto il resto
        try {
            return Integer.parseInt(numberStr); // Converte in intero
        } catch (NumberFormatException e) {
            return 0; // Valore di default se non ci sono numeri
        }
    }
    
    public void handleStartSimulationClick()
    {
    	numberOfSimulations = simulations.size();
    	layout.getControlPanel().getChildren().clear(); 
    	layout.getArrivalTimeMessages().clear();
    	layout.getNeverArriveMessages().clear();
    	layout.getMassArrivalMessagesBox().getChildren().clear();
    	
    	layout.getControlPanel().getChildren().addAll(layout.getSimulatingMessage(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox());
    	
    	for(int i=0; i<simulations.size(); i++)
    	{
    		simulations.get(i).startAnimation();
    	}
    }
    
    public void handleGravitySelection(PlanetIcon iconType)
    {
    	double gMm;
    	switch(iconType) {
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
    	System.out.println("g : " + g);
    	layout.getControlPanel().getChildren().clear();
    	layout.getControlPanel().getChildren().addAll(layout.getStartPointMessage(), layout.getBtnCancelInput());
    	state = UIStates.WAITING_FOR_START_POINT;
    }
    
    @Override 
    public void onWindowResizing()
    {
    	handleCancelInputClick();
    }

}
