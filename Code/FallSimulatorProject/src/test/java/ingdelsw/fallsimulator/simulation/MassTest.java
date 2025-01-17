package ingdelsw.fallsimulator.simulation;

import ingdelsw.fallsimulator.math.Point;
import ingdelsw.fallsimulator.simulation.Mass;
import ingdelsw.fallsimulator.simulation.MassIcon;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MassTest {
    private static Image testImage;

    @BeforeClass
    public static void setUpClass() {
        // Inizializza il toolkit JavaFX una sola volta
        Platform.startup(() -> {});
        // Carica un'immagine di test
        testImage = new Image(MassTest.class.getResource("/images/Newton.png").toExternalForm());
    }

    @Test
    public void testConstructorInitialization() {
        Point startPosition = new Point(50, 100);
        ImageView icon = new ImageView(testImage);
        Mass mass = new Mass(startPosition, MassIcon.NEWTON, icon);

        // Verifica valori iniziali
        assertEquals("La posizione iniziale dovrebbe corrispondere a quella fornita", startPosition, mass.getCurrentPosition());
        assertEquals("Il tipo di icona dovrebbe essere NEWTON", "NEWTON", mass.getIconTypeString());
        assertEquals("Il diametro della massa dovrebbe essere corretto", 40, mass.getMassDiameter(), 0.01);

        // Verifica proprietà iniziali
        assertEquals("La proprietà X iniziale dovrebbe essere corretta", 30.0, mass.xProperty(), 0.01);
        assertEquals("La proprietà Y iniziale dovrebbe essere corretta", 80.0, mass.yProperty(), 0.01);
    }


    @Test
    public void testSetCurrentPosition() {
        Point initialPosition = new Point(50, 50);
        Point newPosition = new Point(60, 60);
        ImageView icon = new ImageView(testImage);

        // Crea l'oggetto Mass
        Mass mass = new Mass(initialPosition, MassIcon.NEWTON, icon);

        // Imposta una nuova posizione
        mass.setCurrentPosition(newPosition);

        // Verifica che la posizione sia aggiornata
        assertEquals("La nuova posizione dovrebbe essere aggiornata correttamente", newPosition, mass.getCurrentPosition());

        // Verifica le proprietà aggiornate
        double expectedX = newPosition.getX() - mass.getMassDiameter() / 2;
        double expectedY = newPosition.getY() - mass.getMassDiameter() / 2;
        assertEquals("La proprietà X dovrebbe essere aggiornata", expectedX, mass.xProperty(), 0.01);
        assertEquals("La proprietà Y dovrebbe essere aggiornata", expectedY, mass.yProperty(), 0.01);
    }

    @Test
    public void testGetIconTypeString() {
        Point startPosition = new Point(50, 100);
        ImageView icon = new ImageView(testImage);
        Mass mass = new Mass(startPosition, MassIcon.NEWTON, icon);

        assertEquals("Il tipo di icona dovrebbe essere NEWTON", "NEWTON", mass.getIconTypeString());
    }

    @Test
    public void testGetIcon() {
        Point startPosition = new Point(50, 100);
        ImageView icon = new ImageView(testImage);
        Mass mass = new Mass(startPosition, MassIcon.NEWTON, icon);

        assertNotNull("L'icona non dovrebbe essere null", mass.getIcon());
        assertEquals("L'icona dovrebbe essere la stessa fornita al costruttore", icon, mass.getIcon());
    }

    @Test
    public void testGetMassDiameter() {
        Point startPosition = new Point(50, 100);
        ImageView icon = new ImageView(testImage);
        Mass mass = new Mass(startPosition, MassIcon.NEWTON, icon);

        assertEquals("Il diametro della massa dovrebbe essere corretto", 40, mass.getMassDiameter(), 0.01);
    }
}
