package application;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MyPane extends BorderPane{
	
	public static Button b1;
	public static TextField t ;
	public static TextField t1;
	public MyPane(double DISTANCE, double NUM_LINES, double WIDTH, double RealHEIGHT, double SIZE)
	{
		double oX = DISTANCE;
		double oY = DISTANCE;
		double HEIGHT = RealHEIGHT * 5/6;
		
		Pane pane = new Pane();
		pane.setPrefHeight(HEIGHT);
		pane.setPrefWidth(WIDTH);
		
		Pane oTherPane = new Pane();
		
		oTherPane.setPadding(new Insets(20, 20, 20, 20));
		
		for(int i = 0;i < NUM_LINES;++i)
		{
			Line lineV = new Line(oX, 0, oX, HEIGHT);
			Line lineH = new Line(0, oY, WIDTH, oY);
			oX += DISTANCE;
			oY += DISTANCE;
			pane.getChildren().addAll(lineV, lineH);
		}
		int x = 0;
		int y = 0;
		for(int i = 0 ;i < NUM_LINES+1 ;++i)
		{
			for(int j = 0 ;j < NUM_LINES+1 ; ++j)
			{
				Rectangle rec = new Rectangle(x, y, SIZE, SIZE);
				if((i+j) % 2 == 0)
				{
					rec.setFill(Color.GREEN);
				}
				else
				{
					rec.setFill(Color.DARKGREEN);
				}
				x += SIZE;
				pane.getChildren().add(rec);
			}
			y +=SIZE;
			x = 0;
		}
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		
		//text field the present score
		t  = new TextField("Score: " + 0);
		t.setEditable(false);
		
		//text field that present the number of safe teleport
		t1  = new TextField("Teleport Safely(SPA): " + 3);
		t1.setEditable(false);
		
		//button the control the teleport randomly
		b1 = new Button("Teleport randomly");

		hbox.getChildren().addAll(b1, t1, t);
		
		oTherPane.getChildren().add(hbox);
		this.setBottom(oTherPane);
		this.setCenter(pane);
//		this.requestFocus();
	}
	
	//set the text of the score board
	public static void showCount(int count)
	{
		t.setText("Score: " + count);
	}
	
	//set the text of the number of teleport safely board.
	public static void showTeleSafe(int num)
	{
		t1.setText("Teleport Safely(SPA): "  + num);
	}
}
