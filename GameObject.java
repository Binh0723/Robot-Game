package application;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class GameObject {
	Pane pane;
	ImageView i;

	public abstract double getX();
	public abstract double getY();
	public abstract void setX(double x);
	public abstract void setY(double y);
}
