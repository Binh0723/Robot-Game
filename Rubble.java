package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Rubble extends GameObject{

	public Rubble()
	{
		pane = new Pane();
		Image img = new Image("application/fire-pixel-art_2021087.jpg");
		i = new ImageView(img);
		i.setFitHeight(25);
		i.setFitWidth(25);
		pane.getChildren().add(i);
	}

	@Override
	public double getX() {
		return i.getTranslateX();
	}

	@Override
	public double getY() {
		return i.getTranslateY();
	}

	@Override
	public void setX(double x) {
		i.setTranslateX(x);

	}

	@Override
	public void setY(double y) {
		i.setTranslateY(y);

	}


}

