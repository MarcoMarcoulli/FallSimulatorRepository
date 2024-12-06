// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.fallsimulator;

import java.util.ArrayList;

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

public class FallSimulator extends Application{

    @Override
    public void start(Stage primaryStage) {
    	
        EventHandler eventHandler = EventHandler.getHandler();
        
        // Configura la finestra per aprirsi massimizzata
        primaryStage.setMaximized(true);
    
        // Configura la scena
        Scene scene = new Scene(eventHandler.getLayout().getBorderPane(), 1000, 700);
        primaryStage.setTitle("Fall Simulator");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
