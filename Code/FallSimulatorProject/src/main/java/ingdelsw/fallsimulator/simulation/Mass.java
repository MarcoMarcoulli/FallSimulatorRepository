// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.fallsimulator.simulation;

import ingdelsw.fallsimulator.enums.MassIcon;
import ingdelsw.fallsimulator.math.Point;
import javafx.scene.image.ImageView;

public class Mass {
    private Point position;
    private MassIcon iconType;
    private ImageView icon;
    private static final double MASSDIAMETER = 40;

    // Costruttore
    public Mass(Point startPosition, MassIcon iconType, ImageView icon) {
        this.position = startPosition;
        this.icon = icon;
        this.icon.setX(position.getX() - MASSDIAMETER/2);
        this.icon.setY(position.getY() - MASSDIAMETER/2);
        this.iconType = iconType;
        icon.setFitWidth(MASSDIAMETER);
        icon.setFitHeight(MASSDIAMETER);
    }

    // Getter e Setter per la posizione corrente
    public Point getCurrentPosition() {
        return position;
    }
    
    public double getMassDiameter()
    {
    	return MASSDIAMETER;
    }

    public void setCurrentPosition(Point newPosition) {
        position = newPosition;
        icon.relocate(position.getX() - MASSDIAMETER/2, position.getY() - MASSDIAMETER/2);
    }

    public String getIconTypeString() {
        return iconType.name();
    }
    
    public ImageView getIcon()
    {
    	return icon;
    }
}