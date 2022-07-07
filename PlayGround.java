package application;


import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PlayGround extends Application{

	//features of the pane
	public static final double  WIDTH = 625;
	public static final double HEIGHT = 750;
	public static final int NUM_LINES = 24;

	//number of robots
	public static final int NUMR = 100;

	//size of the robot
	public static final double SIZE = 25;

	//number of safe teleport
	public static  int countTS = 10;

	//2D array that contains the object
	public static final GameObject[][] pack = new GameObject[25][25];

	public static ArrayList<GameObject> packSp = new ArrayList<>();

	//the distance between the vertical line
	public static final double DISTANCE = WIDTH/(NUM_LINES+1);

	@Override
	public void start(Stage primaryStage)
	{
		MyPane pane = new MyPane(DISTANCE, NUM_LINES, WIDTH, HEIGHT, SIZE);


		PlayerObject p = new PlayerObject();

		//set the initial position of the player
		pack[(int)(p.getX()/SIZE)][(int)(p.getX()/SIZE)] = p;
		pane.getChildren().addAll(p.pane);

		//create bunch of robots
		int numR = 0;
		while(numR < NUMR)
		{
			int r1 = (int)(Math.random()*25);
			int r2 = (int)(Math.random()*25);
			if(!(pack[r1][r2] instanceof RobotObject) && !(pack[r1][r2] instanceof PlayerObject))
			{
				pack[r1][r2] = new RobotObject();
				packSp.add(pack[r1][r2]);
				RobotObject r = (RobotObject) pack[r1][r2]; 

				r.setX(r1 * SIZE );
				r.setY(r2 * SIZE );

				pane.getChildren().add(r.pane);
				numR++;
			}
		}

		//choose the position to move the player
		pane.setOnMouseClicked(e ->{
			double xM = e.getX();
			double yM = e.getY();
			if(!isGameOverLose(p,pane) && !isGameOverWin(pane))
			{
				//move the player
				p.move(xM, yM, p, pane, packSp, SIZE,HEIGHT * 5/6);

				//move the robot then check if the robot is collide or not
				moveAllRobot(p, yM, pane);
				checkRobotIsColllide(pane);

				//update the score
				pane.showCount((NUMR - numRobs())*10);

				//check if the game is over or not
				isGameOverWin(pane);
				isGameOverLose(p,pane);
			}

		});

		//teleport the player to the safe place
		pane.setOnKeyPressed(e ->{
			while(!isGameOverLose(p,pane) && !isGameOverWin(pane) && countTS > 0 && e.getCode() == KeyCode.SPACE)
			{
				double x = (int)(Math.random() * 25);
				double y = (int)(Math.random() * 25);

				double newX = x * SIZE;
				double newY = y * SIZE;

				if(!checkIfFire(packSp, newX, newY) && !checkIfRobot(packSp, newX, newY))
				{
					//set the position that safely
					p.setX(newX);
					p.setY(newY);
					countTS--;

					//update the number of safe teleport
					pane.showTeleSafe(countTS);
					e.consume();
					break;

				}

			}
		});


		//teleport randomly
		pane.b1.setOnAction(e -> {
			while(!isGameOverLose(p,pane))
			{
				double x = (int)(Math.random()*25) * SIZE;
				double y = (int)(Math.random()*25) * SIZE;

				if(checkIfFire(packSp,x,y))
				{
					continue;
				}
				else
				{
					p.setX(x);
					p.setY(y);
					isGameOverLose(p,pane);
					break;
				}
			}
		});



		Scene scene = new Scene(pane,WIDTH,HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
		pane.requestFocus();
	}
	public static void main(String[] args) {
		launch(args);
	}

	//move all the robot
	public static void moveAllRobot(PlayerObject p,double yM, BorderPane pane)
	{
		for(int i = 0;i < packSp.size();++i)
		{

			if(packSp.get(i) instanceof RobotObject)
			{
				RobotObject newRobot = (RobotObject)(packSp.get(i));
				newRobot.move(p, SIZE,yM, HEIGHT *5/6);
				isGameOverLose(p, pane);
			}

		}
	}

	//check if the game is over (when lose)
	public static boolean isGameOverLose(PlayerObject p, BorderPane pane)
	{
		double x = p.getX();
		double y = p.getY();


		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof RobotObject)
			{
				RobotObject rb = (RobotObject)packSp.get(i);
				double rbX = rb.getX();
				double rbY = rb.getY();
				if(rbX == x && rbY ==y)
				{

					TextField t = new TextField("YOU LOSE");
					pane.setTop(t);

					//create the picture of the death people
					DeathPeople dp = new DeathPeople();
					pane.getChildren().removeAll(p.pane, rb.pane);
					pane.getChildren().add(dp.pane);
					dp.setX(x);
					dp.setY(y);
					return true;
				}
			}
		}
		return false;
	}

	//check if the game is over (when win)
	public static boolean isGameOverWin(BorderPane pane)
	{
		int count = 0;
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof RobotObject)
			{
				count++;
			}
		}
		if(count == 0)
		{
			TextField t = new TextField("YOU WIN");
			pane.setTop(t);
			return true;

		}
		return false;

	}

	//check if robot is collide with each other or not
	public static void checkRobotIsColllide(Pane pane)
	{
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof RobotObject)
			{
				RobotObject rb = (RobotObject)packSp.get(i);
				rb.isCollide(packSp, pane);
			}
		}
	}

	//check if the position is the same as the position of rubble  or not
	public static boolean checkIfFire(ArrayList<GameObject> packSp, double x, double y)
	{
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof Rubble)
			{
				Rubble rb = (Rubble)packSp.get(i);

				double rbX = rb.getX();
				double rbY = rb.getY();

				if(rbX == x && rbY == y)
				{
					return true;
				}
			}
		}
		return false;
	}

	//use to calculate the score 
	public static int numRobs()
	{
		int count = 0;
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof RobotObject)
			{
				count++;
			}
		}
		return count;
	}

	//check if the position is the same as the position of rubble  or not
	public static boolean checkIfRobot(ArrayList<GameObject> packSp, double x, double y)
	{
		for(int i = 0;i < packSp.size();++i)
		{
			if(packSp.get(i) instanceof RobotObject)
			{
				RobotObject rb = (RobotObject)packSp.get(i);

				double rbX = rb.getX();
				double rbY = rb.getY();

				if(rbX == x && rbY == y)
				{
					return true;
				}
			}
		}
		return false;
	}

}
