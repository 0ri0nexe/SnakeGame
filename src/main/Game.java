package main;

import java.io.IOException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game extends AnimationTimer {

    // screen setup
	static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    private Stage stage;
    private Scene scene = new Scene(new Group(), SCREEN_WIDTH, SCREEN_HEIGHT);

    // game
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
    boolean running = true;
	Random random;

    // timer things
	private int FPS = 100000000; // not really "FPS", most like "snake and objects refreshing delay"
    private double startTime;
    
    // snake
    final int x[] = new int[SCREEN_WIDTH*UNIT_SIZE];
    final int y[] = new int[SCREEN_HEIGHT*UNIT_SIZE];
	int bodyParts = 6;
	char direction = 'R';

    // apple
    Apple apple;
	int applesEaten;

    // input managing
    boolean inputUsed = false;

    Game(App main){
        stage = main.getStage();
        apple = new Apple(stage, SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE);
        primarySetup();
    }

    Game(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        apple = new Apple(stage, SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE);
        primarySetup();
    }

    private void gameOver(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("gameOverMenu.fxml"));
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stop();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void primarySetup() {
        random = new Random();

        // set the stage and if stop the windows it'll not run it again
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent evt) {
                stop();
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!inputUsed){
                    switch (event.getCode()) {
                        case UP:    
                            if (direction != 'D'){
                                direction = 'U';
                                inputUsed = true;
                                break;
                            }
                        case DOWN:  
                            if (direction != 'U'){
                                direction = 'D';
                                inputUsed = true;
                                break;
                            }
                        case LEFT:  
                            if (direction != 'R'){
                                direction = 'L'; 
                                inputUsed = true;
                                break;
                            }
                        case RIGHT: 
                            if (direction != 'L'){
                                direction = 'R';
                                inputUsed = true;
                                break;
                            }
                        default:
                            break;
                    }
                }
            }
        });
    }

    private Group setGrid(Group gridRoot){

        for(int i=0; i<SCREEN_WIDTH/UNIT_SIZE; i++) {

            Line verticalLine = new Line(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            verticalLine.setStroke(Color.LIGHTGRAY);
            gridRoot.getChildren().add(verticalLine);

            Line horizontalLine = new Line(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            horizontalLine.setStroke(Color.LIGHTGRAY);
            gridRoot.getChildren().add(horizontalLine);
        }
        return gridRoot;
    }

    private void setup(){
        Group gameRoot = new Group();
        gameRoot = setGrid(gameRoot);
        scene.setRoot(gameRoot);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    private void checkCollisions(){
        //checks if head collides with body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		//check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if(x[0] >= SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0] >= SCREEN_HEIGHT) {
			running = false;
		}

        if (!running){
            gameOver();
        }
    }

    public void checkApple() {
		if ((x[0] == apple.getX()) && (y[0] == apple.getY())){
            bodyParts++;
            applesEaten++;
            apple.setOnTheGrid(false);
            apple.newApple();
        }
	}



    private void update(){
        // move snake
        inputUsed = false;
        for (int i = bodyParts; i > 0; i--){
			x[i] = x[i-1];
			y[i] = y[i - 1];
		}

        switch(direction) {
			case 'U' :
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D' :
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L' :
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R' :
				x[0] = x[0] + UNIT_SIZE;
				break;
		}

        // draw snake
        for (int i = 0; i < bodyParts; i++) {

            Rectangle bodyPart = new Rectangle(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

            if(i==0) {   
                bodyPart.setFill(Color.rgb(59, 236, 15));
            }
            else {
                bodyPart.setFill(Color.rgb(70, 207, 40));
            }

            Group gameRoot = (Group)scene.getRoot();
            gameRoot.getChildren().add(bodyPart);
        }
        checkCollisions();
    }

    @Override
    public void handle(long arg0) {
        double currentTime = System.nanoTime();
        if (FPS <= (currentTime - startTime)){
            startTime = currentTime;
            //set a new scene
            Group emptyRoot = new Group();
            scene.setRoot(emptyRoot);;
            setup();
            update();
            checkApple();
            if (!apple.isOnTheGrid()){
                apple.newApple();
            }
            else{
                apple.draw();
            }
        }     
    }
}