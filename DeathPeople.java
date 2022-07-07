package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

	public class DeathPeople extends GameObject{
		public DeathPeople()
		{
			pane = new Pane();
			Image img = new Image("application/death.jpg");
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


