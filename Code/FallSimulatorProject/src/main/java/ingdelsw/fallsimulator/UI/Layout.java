/*
 * Layout.java;
 * 
 * manages user interface layout and elements
 */
package ingdelsw.fallsimulator.UI;

import java.util.ArrayList;
import java.util.List;

import ingdelsw.fallsimulator.enums.MassIcon;
import ingdelsw.fallsimulator.enums.PlanetIcon;
import ingdelsw.fallsimulator.observers.WindowResizingObserver;
//javaFX imports
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Layout {
	//main layout
    private BorderPane root;
    
    //buttons and messages panel
    private VBox controlPanel;
    
    //points insertion layout
    private Canvas pointsCanvas;
    
    //curves drawing layout
    private Canvas curveCanvas;
    
    //simulation layout
    private Pane animationPane;
    
    //layout overlap
    private StackPane stackPane;
    
    //buttons containers
    private HBox curveButtons;
    private HBox massIconButtons;
    private HBox convexityButtons;
    private HBox planetIconButtons;
    
    //interface messages declaration
    private Label startPointMessage;
    private Label endPointMessage;
    private Label chooseCurveMessage;
    private Label intermediatePointsMessage;
    private Label chooseMassMessage;
    private Label chooseRadiusMessage;
    private Label chooseConvexityMessage;
    private Label selectGravityMessage;
    private Label simulatingMessage;
    private List<Label> arrivalTimeMessages;
    private List<Label> neverArriveMessages;
    
    //buttons declarations
    private Button btnCancelInput;
    private Button btnCycloid;
    private Button btnParabola;
    private Button btnCubicSpline;
    private Button btnCircumference;
    private Button btnConfirmRadius;
    private Button btnConvexityUp;
    private Button btnConvexityDown;
    private Button btnStopIntermediatePointsInsertion;
    private Button btnStartSimulation;
    private Button btnInsertAnotherCurve;
    
    //slider for radius input
    private Slider radiusSlider;
    
    //times messages container
    private VBox massArrivalMessagesBox;
    
    //masses icons
    private VBox iconViewBernoulli;
    private VBox iconViewGalileo;
    private VBox iconViewJakob;
    private VBox iconViewLeibnitz;
    private VBox iconViewNewton;
    
    //planets icons
    private VBox iconViewMoon;
    private VBox iconViewMars;
    private VBox iconViewEarth;
    private VBox iconViewJupiter;
    private VBox iconViewSun;
    
    //graphics contents drawer
    private GraphicsContext gc;
    
    //singleton instance
    private static Layout theLayout = null;
    
    //listener for handling window resizing
    private WindowResizingObserver observer;
    
    //interface constructor
    private Layout() {
    	
    	root = new BorderPane();
        root.getStylesheets().add(
        	    getClass().getClassLoader().getResource("style.css").toExternalForm()
        	);
        
        controlPanel = new VBox(10);
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.setPrefWidth(435);
        controlPanel.setMinWidth(435);
        
        curveCanvas = new Canvas();
        pointsCanvas = new Canvas();
        animationPane = new Pane();
        animationPane.setMouseTransparent(true); 
        
        curveButtons = new HBox(10);
        curveButtons.getStyleClass().add("curve-buttons");
        
        massIconButtons = new HBox(50); 
        massIconButtons.getStyleClass().add("icon-buttons");
        
        planetIconButtons = new HBox(50); 
        planetIconButtons.getStyleClass().add("icon-buttons");
        
        convexityButtons= new HBox(6);
        
        massArrivalMessagesBox = new VBox();
        
        stackPane = new StackPane(curveCanvas, pointsCanvas, animationPane);
        
        radiusSlider = null;
        
        selectGravityMessage = new Label("scegli il campo gravitazionale".toUpperCase());
        controlPanel.getChildren().addAll(selectGravityMessage, planetIconButtons);
        startPointMessage = new Label("Inserisci il punto di partenza".toUpperCase());
        final String label = "label";
        startPointMessage.getStyleClass().add(label);
        controlPanel.getChildren().add(startPointMessage);
        endPointMessage = new Label("Inserisci il punto di arrivo".toUpperCase());
        endPointMessage.getStyleClass().add(label);
        chooseCurveMessage = new Label("scegli una curva".toUpperCase());
        chooseCurveMessage.getStyleClass().add(label);
        intermediatePointsMessage = new Label("Inserisci dei punti intermedi da interpolare".toUpperCase());
        chooseMassMessage = new Label("Inserisci chi vuoi far scivolare".toUpperCase()); 
        chooseRadiusMessage = new Label("Seleziona il raggio della circonferenza".toUpperCase());
        chooseConvexityMessage = new Label("scegli la convessita".toUpperCase());
        simulatingMessage = new Label("Simulazione in corso".toUpperCase());
        
        btnCancelInput = new Button("Cancella Input");
        final String button = "button";
        btnCancelInput.getStyleClass().add(button);
        btnCancelInput.getStyleClass().add("cancel-button");
        btnCubicSpline = new Button("Spline Cubica");
        btnCubicSpline.getStyleClass().add(button);
        btnCycloid = new Button("Cicloide");
        btnCycloid.getStyleClass().add(button);
        btnParabola = new Button("Parabola");
        btnParabola.getStyleClass().add(button);
        btnCircumference = new Button("Circonferenza");
        btnCircumference.getStyleClass().add(button);
        btnConfirmRadius = new Button("Seleziona Raggio");
        btnConvexityUp = new Button("Verso l'alto");
        btnConvexityDown = new Button("Verso il basso");
        btnStopIntermediatePointsInsertion = new Button("Fine immissione");
        btnStartSimulation = new Button("Avvia simulazione");
        btnInsertAnotherCurve = new Button("Inserisci un' altra curva");
        
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        convexityButtons.getChildren().addAll(btnConvexityUp, btnConvexityDown);
        
        Image iconBernoulli = new Image(getClass().getResource("/images/Bernoulli.png").toExternalForm());
        Image iconGalileo = new Image(getClass().getResource("/images/Galileo.png").toExternalForm());
        Image iconJakob = new Image(getClass().getResource("/images/Jakob.png").toExternalForm());
        Image iconLeibnitz = new Image(getClass().getResource("/images/Leibnitz.png").toExternalForm());
        Image iconNewton = new Image(getClass().getResource("/images/Newton.png").toExternalForm());

        iconViewBernoulli = createMassIconButton(iconBernoulli, "BERNOULLI");
        iconViewGalileo = createMassIconButton(iconGalileo, "GALILEO");
        iconViewJakob = createMassIconButton(iconJakob, "JAKOB");
        iconViewLeibnitz = createMassIconButton(iconLeibnitz, "LEIBNITZ");
        iconViewNewton = createMassIconButton(iconNewton, "NEWTON");
        
        massIconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        
        Image iconMoon = new Image(getClass().getResource("/images/moon.png").toExternalForm());
        Image iconMars = new Image(getClass().getResource("/images/mars.png").toExternalForm());
        Image iconEarth = new Image(getClass().getResource("/images/earth.png").toExternalForm());
        Image iconJupiter = new Image(getClass().getResource("/images/jupiter.png").toExternalForm());
        Image iconSun = new Image(getClass().getResource("/images/sun.png").toExternalForm());

        iconViewMoon = createPlanetIconButton(iconMoon,"LUNA", "g = 1,62");
        iconViewMars = createPlanetIconButton(iconMars,"MARTE", "g = 3,73");
        iconViewEarth = createPlanetIconButton(iconEarth,"TERRA", "g = 9,81");
        iconViewJupiter = createPlanetIconButton(iconJupiter,"GIOVE", "g = 24,79");
        iconViewSun = createPlanetIconButton(iconSun,"SOLE", "g = 274");
        
        planetIconButtons.getChildren().addAll(iconViewMoon, iconViewMars, iconViewEarth, iconViewJupiter, iconViewSun);
        
        arrivalTimeMessages = new ArrayList<>();
        neverArriveMessages = new ArrayList<>();
        
        gc = pointsCanvas.getGraphicsContext2D();
        
        stackPane = new StackPane();
        stackPane.getChildren().addAll(curveCanvas, pointsCanvas, animationPane);
        root.setCenter(stackPane);
        
        root.setLeft(controlPanel);
        
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue() - controlPanel.getWidth();
            curveCanvas.setWidth(newWidth);
            pointsCanvas.setWidth(newWidth);
            animationPane.setPrefWidth(newWidth);
            notifyObserver();
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            curveCanvas.setHeight(newHeight);
            pointsCanvas.setHeight(newHeight);
            animationPane.setPrefHeight(newHeight);
            notifyObserver();
        });
        
    }
    
    public void addWindowResizingObserver(WindowResizingObserver observer)
    {
    	this.observer=observer;
    }
    
    
    //notify method observer pattern
    private void notifyObserver()
    {
    	observer.onWindowResizing();
    }
    
    //singleton instance getter
    public static Layout getLayout(WindowResizingObserver listener)
    {
    	if(theLayout == null)
    		theLayout = new Layout();
    	return theLayout;
    }
    
    //diametro dei pulsanti 
    private static final double ICONBUTTONDIAMETER = 70;
    
    // Metodi helper per creare un pulsante icona
    private VBox createMassIconButton(Image image, String text) {
        ImageView iconView = new ImageView(image);
        iconView.setFitWidth(ICONBUTTONDIAMETER);
        iconView.setFitHeight(ICONBUTTONDIAMETER);
        
        Label caption = new Label(text);
        caption.getStyleClass().add("label-masses");

        VBox vbox = new VBox(4); 
        vbox.setAlignment(Pos.CENTER); 
        vbox.getChildren().addAll(iconView, caption);

        return vbox;
    }
    
    private VBox createPlanetIconButton(Image image, String text1, String text2) {
        VBox vbox = createMassIconButton(image, text1);
        Label g = new Label(text2);
        g.getStyleClass().add("label-masses");
        vbox.getChildren().add(g);
        return vbox;
    }
    
    
    //getter 
    public BorderPane getBorderPane()
    {
    	return root;
    }

    public VBox getControlPanel() {
        return controlPanel;
    }

    public Canvas getPointsCanvas() {
        return pointsCanvas;
    }

    public Canvas getCurveCanvas() {
        return curveCanvas;
    }

    public Pane getAnimationPane() {
        return animationPane;
    }

    public HBox getCurveButtons() {
        return curveButtons;
    }

    public HBox getMassIconButtons() {
        return massIconButtons;
    }
    
    public HBox getPlanetIconButtons() {
        return planetIconButtons;
    }

    public HBox getConvexityButtons() {
        return convexityButtons;
    }

    public VBox getMassArrivalMessagesBox() {
        return massArrivalMessagesBox;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public Label getStartPointMessage() {
        return startPointMessage;
    }

    public Label getEndPointMessage() {
        return endPointMessage;
    }

    public Label getChooseCurveMessage() {
        return chooseCurveMessage;
    }

    public Label getIntermediatePointsMessage() {
        return intermediatePointsMessage;
    }

    public Label getChooseMassMessage() {
        return chooseMassMessage;
    }
    
    public Label getSelectGravityMessage() {
        return selectGravityMessage;
    }

    public Label getChooseRadiusMessage() {
        return chooseRadiusMessage;
    }

    public Label getChooseConvexityMessage() {
        return chooseConvexityMessage;
    }

    public Label getSimulatingMessage() {
        return simulatingMessage;
    }

    public Button getBtnCancelInput() {
        return btnCancelInput;
    }

    public Button getBtnCycloid() {
        return btnCycloid;
    }

    public Button getBtnParabola() {
        return btnParabola;
    }

    public Button getBtnCubicSpline() {
        return btnCubicSpline;
    }

    public Button getBtnCircumference() {
        return btnCircumference;
    }

    public Button getBtnConfirmRadius() {
        return btnConfirmRadius;
    }

    public Button getBtnConvexityUp() {
        return btnConvexityUp;
    }

    public Button getBtnConvexityDown() {
        return btnConvexityDown;
    }

    public Button getBtnStopIntermediatePointsInsertion() {
        return btnStopIntermediatePointsInsertion;
    }

    public Button getBtnStartSimulation() {
        return btnStartSimulation;
    }
    
    public Button getBtnInsertAnotherCurve() {
        return btnInsertAnotherCurve;
    }

    public Slider getRadiusSlider() {
        return radiusSlider;
    }
    
    public void setRadiusSlider(Slider radiusSlider) {
        this.radiusSlider = radiusSlider;
    }
    
    public ImageView getPlanet(PlanetIcon planet)
    {
    	switch(planet) {
    		case MOON: return (ImageView)iconViewMoon.getChildren().get(0);
    		case MARS: return (ImageView)iconViewMars.getChildren().get(0);
    		case EARTH: return (ImageView)iconViewEarth.getChildren().get(0);
    		case JUPITER: return (ImageView)iconViewJupiter.getChildren().get(0);
    		case SUN: return (ImageView)(iconViewSun.getChildren()).get(0);
    		default: return null;
    	}
    }
    
    public ImageView getMass(MassIcon mass)
    {
    	switch(mass) {
    		case BERNOULLI: return (ImageView)iconViewBernoulli.getChildren().get(0);
    		case JAKOB: return (ImageView)iconViewJakob.getChildren().get(0);
    		case GALILEO: return (ImageView)iconViewGalileo.getChildren().get(0);
    		case LEIBNITZ: return (ImageView)iconViewLeibnitz.getChildren().get(0);
    		case NEWTON: return (ImageView)iconViewNewton.getChildren().get(0);
    		default: return null;
    	}
    }
    
    public GraphicsContext getGC()
    {
    	return gc;
    }
    
    public void clear()
    {
    	pointsCanvas.getGraphicsContext2D().clearRect(0, 0, pointsCanvas.getWidth(), pointsCanvas.getHeight());
        curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
        animationPane.getChildren().clear();
        controlPanel.getChildren().clear();
        controlPanel.getChildren().addAll(selectGravityMessage, planetIconButtons);
        massIconButtons.getChildren().clear();
        massIconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        curveButtons.getChildren().clear();
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        arrivalTimeMessages.clear();
    	neverArriveMessages.clear();
    	massArrivalMessagesBox.getChildren().clear();
    }

	public List<Label> getArrivalTimeMessages() {
		return arrivalTimeMessages;
	}
	
	public List<Label> getNeverArriveMessages() {
		return neverArriveMessages;
	}
}