package main;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Apple {
    private int x;
    private int y;
    private boolean onTheGrid;
    
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int UNIT_SIZE;
    private Stage stage;
    private Scene scene;

    private Random r = new Random();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOnTheGrid(){
        return onTheGrid;
    }

    public void setOnTheGrid(boolean isOnTheGrid){
        onTheGrid = isOnTheGrid;
    }

    Apple(Stage stage, int SCREEN_WIDTH, int SCREEN_HEIGHT, int UNIT_SIZE){
        this.stage = stage;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.UNIT_SIZE = UNIT_SIZE;
    }

    public void newApple() {
        scene = stage.getScene();
		x = r.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		y = r.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        Group root = (Group)scene.getRoot();
        Circle circle = new Circle();
        circle.setCenterX(x + UNIT_SIZE/2);
        circle.setCenterY(y + UNIT_SIZE/2);
        circle.setRadius(UNIT_SIZE/2);
        circle.setFill(Color.RED);
        root.getChildren().add(circle);
        scene.setRoot(root);
        stage.show();
        onTheGrid = true;
	}

    public void draw(){
        scene = stage.getScene();
        Group root = (Group)scene.getRoot();
        Circle circle = new Circle();
        circle.setCenterX(x + UNIT_SIZE/2);
        circle.setCenterY(y + UNIT_SIZE/2);
        circle.setRadius(UNIT_SIZE/2);
        circle.setFill(Color.RED);
        root.getChildren().add(circle);
        scene.setRoot(root);
        stage.show();
    }
}
