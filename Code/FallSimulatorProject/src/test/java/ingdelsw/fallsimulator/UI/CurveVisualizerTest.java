package ingdelsw.fallsimulator.UI;

import static org.junit.Assert.*;

import org.junit.Test;

import ingdelsw.fallsimulator.math.Point;
import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurveVisualizerTest {

    @Test
    public void testDrawCurve() {
        // Inizializza JavaFX
        new JFXPanel();

        // Crea un canvas per il test
        Canvas canvas = new Canvas(200, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Crea punti di test
        Point[] points = new Point[] {
            new Point(10, 10),
            new Point(50, 50),
            new Point(100, 100)
        };

        // Colore atteso
        int red = 255;
        int green = 0;
        int blue = 0;

        // Chiama il metodo drawCurve
        CurveVisualizer.drawCurve(points, gc, red, green, blue);

        // Verifica il colore impostato
        Color expectedColor = Color.rgb(red, green, blue);
        assertEquals("Il colore del tratto non è corretto.", expectedColor, gc.getStroke());

        // Verifica la larghezza della linea
        assertEquals("La larghezza della linea non è corretta.", 2, gc.getLineWidth(), 0.001);

        // Verifica che non ci siano state eccezioni durante l'esecuzione
        assertTrue("Il metodo drawCurve è stato eseguito senza errori.", true);
    }
}

