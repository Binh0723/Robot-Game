package application;


import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PlayerObject extends GameObject{


	public PlayerObject()
	{
		Image img = new Image("application/melbvg(1).png");
		i = new ImageView(img);
		i.setFitHeight(25);
		i.setFitWidth(25);
		pane = new Pane();
		pane.getChildren().add(i);
		i.setTranslateX(300);
		i.setTranslateY(300);
	}

	public double getX()
	{
		return i.getTranslateX();
	}
	public double getY()
	{
		return i.getTranslateY();
	}

	public void setX(double x)
	{
		i.setTranslateX(x);
	}
	public void setY(double y)
	{
		i.setTranslateY(y);
	}

	//method that move the player according to the position of the mouse clicked
	public void move(double xM, double yM, PlayerObject p, Pane pane, ArrayList<GameObject> packSp, double SIZE, double HEIGHT)
	{
		double centerX = p.getX() + SIZE/2;
		double centerY = p.getY() + SIZE/2;


		double tan = (yM - centerY)/(xM - centerX);

		if(yM >= 0 && yM <= HEIGHT)
		{
			if(checkInS(p.getX(), p.getY(), xM, yM, SIZE) || checkInFireZone(packSp, xM, yM, SIZE))
			{
				//do nothing
			}
			else if (xM > p.getX())
			{
				if(xM - p.getX() <= SIZE)
				{
					if(yM - p.getY() <= 0)
					{
						p.setY(p.getY() - SIZE);
					}

					else
					{
						p.setY(p.getY() + SIZE);
					}
				}
				else
				{
					if (yM - p.getY() >=0 && yM - p.getY() <= SIZE)
					{
						p.setX(p.getX() + SIZE);
					}
					else if (tan < -1/3)
					{
						p.setY(p.getY() - SIZE);
						p.setX(p.getX() + SIZE);
					}
					else if (tan > 1/3)
					{
						p.setY(p.getY() + SIZE);
						p.setX(p.getX() + SIZE);
					}
				}

			}
			else
			{
				if(yM - p.getY() >= 0 && yM - p.getY() < SIZE)
				{
					p.setX(p.getX() - SIZE);
				}
				else if(tan >= 1/3)
				{
					p.setY(p.getY() - SIZE);
					p.setX(p.getX() - SIZE);
				}
				else if(tan <= -1/3)
				{
					p.setY(p.getY() + SIZE);
					p.setX(p.getX() - SIZE);
				}
			}
		}
	}

	//check if the mouse clicked is in the position where the player standing or not
	public boolean checkInS(double x, double y, double xM, double yM, double size)
	{
		if(xM - x <= size && xM -x >=0 && yM - y <= size && yM -y >=0)
		{
			return true;
		}
		return false;
	}


	//check if the mouse click is in the fire zone or not
	public boolean checkInFireZone(ArrayList<GameObject> packSp, double x, double y, double SIZE)
	{
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof Rubble)
			{
				Rubble rb = (Rubble)packSp.get(i);

				double rbX = rb.getX();
				double rbY = rb.getY();

				if(x- rbX >= 0 && x - rbX <= SIZE && y - rbY >=0 && y - rbY <= SIZE)
				{
					return true;
				}
			}
		}
		return false;
	}
}
