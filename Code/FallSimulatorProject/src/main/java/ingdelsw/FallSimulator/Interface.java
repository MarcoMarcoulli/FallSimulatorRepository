// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.FallSimulator;

import java.util.ArrayList;

import ingdelsw.FallSimulator.InputManager;
import ingdelsw.FallSimulator.SimulationManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interface extends Application{
				
	private InputManager inputManager;
    private ArrayList<SimulationManager> simulations;
    private UIStates state;
    
    private Canvas pointsCanvas;
    private Canvas curveCanvas;
    private Pane animationPane;
    private VBox controlPanel;
    private HBox curveButtons, iconButtons;
    private Label startPointMessage, endPointMessage, chooseCurveMessage, intermediatePointsMessage, chooseMassMessage, 
    			  chooseRadiusMessage, chooseConvexityMessage, simulatingMessage;
    private Button btnCancelInput, btnCycloid, btnParabola, btnCubicSpline, btnCircumference, btnConfirmRadius, btnConvexityUp, 
    			   btnConvexityDown, btnStopIntermediatePointsInsertion, btnStartSimulation, btnInsertAnotherCurve; 
    private ImageView iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton;
    private Slider radiusSlider;
    
    public enum UIStates {
    	WAITING_FOR_START_POINT,
        WAITING_FOR_END_POINT,
        CHOOSING_CURVE,
        INSERTING_INTERMEDIATE_POINTS,
        CHOOSING_CONVEXITY,
        CHOOSING_RADIUS,
        CHOOSING_MASS,
        WAITING_TO_START_SIMULATION,
        SIMULATING,
        SHOWING_SIMULATION_RESULTS;
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// Layout principale
        BorderPane root = new BorderPane();
        
        startPointMessage = new Label("Inserisci il punto di partenza".toUpperCase());
        endPointMessage = new Label("Inserisci il punto di arrivo".toUpperCase());
        chooseCurveMessage = new Label("scegli una curva".toUpperCase());
        intermediatePointsMessage = new Label("Inserisci dei punti intermedi da interpolare".toUpperCase());
        chooseMassMessage = new Label("Inserisci chi vuoi far scivolare".toUpperCase()); 
        chooseRadiusMessage = new Label("Seleziona il raggio della circonferenza".toUpperCase());
        chooseConvexityMessage = new Label("scegli la convessita".toUpperCase());
        simulatingMessage = new Label("Simulazione in corso".toUpperCase());
        
        btnCancelInput = new Button("Cancella Input");
        btnCancelInput.getStyleClass().add("button");
        btnCancelInput.getStyleClass().add("cancel-button");
        // Pulsanti per le curve
        btnCubicSpline = new Button("Spline Cubica");
        btnCubicSpline.getStyleClass().add("button");
        btnCycloid = new Button("Cicloide");
        btnCycloid.getStyleClass().add("button");
        btnParabola = new Button("Parabola");
        btnParabola.getStyleClass().add("button");
        btnCircumference = new Button("Circonferenza");
        btnCircumference.getStyleClass().add("button");
        btnConfirmRadius = new Button("Seleziona Raggio");
        btnConvexityUp = new Button("verso l'alto");
        btnConvexityDown = new Button("verso il basso");
        btnStopIntermediatePointsInsertion = new Button("Fine immissione");
        btnStartSimulation = new Button("avvia simulazione");
        btnInsertAnotherCurve = new Button("inserisci un' altra curva");
        
        // Carica le icone
        Image iconBernoulli = new Image(getClass().getResource("/images/Bernoulli.png").toExternalForm());
        Image iconGalileo = new Image(getClass().getResource("/images/Galileo.png").toExternalForm());
        Image iconJakob = new Image(getClass().getResource("/images/Jakob.png").toExternalForm());
        Image iconLeibnitz = new Image(getClass().getResource("/images/Leibnitz.png").toExternalForm());
        Image iconNewton = new Image(getClass().getResource("/images/Newton.png").toExternalForm());
        
        // Pannello di controllo (a sinistra)
        controlPanel = new VBox(10);
        controlPanel.setPrefWidth(308); // Imposta la larghezza predefinita
        controlPanel.setStyle("-fx-background-color: lightgray;"); // Colore grigio chiaro per il controllo
        root.setLeft(controlPanel);
        
        // Canvas per disegno (a destra)
        curveCanvas = new Canvas();
        pointsCanvas = new Canvas();
        
        // Aggiunge entrambi i Canvas al centro del layout
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(curveCanvas, pointsCanvas);
        root.setCenter(stackPane);
        
        root.setLeft(controlPanel);
        
        inputManager = new InputManager();
        simulations = new ArrayList<>();
        
        // Configura la finestra per aprirsi a schermo intero 
        primaryStage.setMaximized(true);
        
        // Configura la scena
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Fall Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
