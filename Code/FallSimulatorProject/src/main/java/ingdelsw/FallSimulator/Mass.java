// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.FallSimulator;

import ingdelsw.ExecutablePrototype.MassIcon;
import ingdelsw.FallSimulator.Math.Point;
import javafx.scene.image.ImageView;

public class Mass {
	
	private Point position;
	private MassIcon iconType;
	private final double massDiameter = 40;
	
	public Mass(Point startPosition, MassIcon iconType, ImageView icon) {
        this.position = startPosition;
        this.icon = icon;
        this.icon.setX(Position.getX() - massDiameter/2);
        this.icon.setY(Position.getY() - massDiameter/2);
        this.iconType = iconType;
        icon.setFitWidth(massDiameter);
        icon.setFitHeight(massDiameter);
    }
	
}
