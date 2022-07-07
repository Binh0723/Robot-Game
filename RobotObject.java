package application;


import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RobotObject extends GameObject{
	//	Pane paneR;
	//	ImageView i2;
	public static int count;

	public RobotObject()
	{
		pane = new Pane();
		Image img = new Image("application/istockphoto-1030996148-1024x1024.jpg");
		i = new ImageView(img);
		i.setFitHeight(25);
		i.setFitWidth(25);
		pane.getChildren().add(i);
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

	public void move(PlayerObject p, double dist, double yM, double HEIGHT)
	{
		if(yM >= 0 && yM <= HEIGHT)
		{
			if (p.getX() == this.getX())
			{
				if (p.getY() > this.getY() )
				{
					this.setY(this.getY() + dist);
				}
				else
				{
					this.setY(this.getY() - dist);
				}
			}
			else if ( p.getY() == this.getY())
			{
				if ( p.getX() > this.getX() )
				{
					this.setX( this.getX() + dist);
				}
				else
				{
					this.setX( this.getX() - dist);
				}
			}
			else if ( this.getX() > p.getX() && this.getY() < p.getY())
			{
				this.setX(this.getX() - dist);
				this.setY(this.getY() + dist);
			}
			else if ( this.getX() > p.getX() && this.getY() > p.getY())
			{
				this.setX( this.getX() - dist);
				this.setY( this.getY() - dist);
			}
			else if ( this.getX() < p.getX() && this.getY() < p.getY())
			{
				this.setX( this.getX() + dist);
				this.setY( this.getY() + dist);
			}
			else if ( this.getX() < p.getX() && this.getY() > p.getY())
			{
				this.setX( this.getX() + dist);
				this.setY( this.getY() - dist);
			}
		}

	}

	//method that check robot is collide with each other and rubble
	public void isCollide(ArrayList<GameObject> packSp, Pane pane)
	{
		//the position of the robot
		double x = this.getX();
		double y = this.getY();

		//boolean that check if collide with robot or rubble
		boolean checkR = false;
		boolean checkF = false;

		//iterate through the ArrayList to check whether hit a robot or a rubble
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof RobotObject)
			{
				RobotObject rb = (RobotObject)packSp.get(i);

				if(this != rb)
				{
					if(x == rb.getX() && y == rb.getY())
					{
						pane.getChildren().remove(rb.pane);
						packSp.remove(i);
						i--;
						checkR = true;

					}
				}
			}
			else if(packSp.get(i) instanceof Rubble)
			{
				Rubble fr = (Rubble)packSp.get(i);
				double fX = fr.getX();
				double fY = fr.getY();

				if(x == fX && y == fY)
				{

					checkF = true;
				}
			}
		}

		if(checkR)
		{
			//remove the picture of the fire out of the pane and remove the Robot out of the ArrayList
			pane.getChildren().remove(this.pane);
			packSp.remove(this);

			//create a new Rubble and add it to the pane and the ArrayList
			Rubble fire = new Rubble();
			pane.getChildren().add(fire.pane);
			fire.setX(x);
			fire.setY(y);
			packSp.add(fire);
		}
		else if(checkF)
		{
			packSp.remove(this);
			pane.getChildren().remove(this.pane);
		}
	}


}
