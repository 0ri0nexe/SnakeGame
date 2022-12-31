package main;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game extends AnimationTimer {

        // screen setup
	static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;

    // game
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
    boolean running = false;
	Random random;

    // timer things
	private int FPS = 10; // not really "FPS", most like "snake and objects refreshing delay"
    private double startTime;
    
    // snake
    final int x[] = new int[SCREEN_WIDTH*UNIT_SIZE];
    final int y[] = new int[SCREEN_HEIGHT*UNIT_SIZE];
	int bodyParts = 6;
	char direction = 'R';

    // apple
    boolean appleOnTheGrid = false;
	int applesEaten;
	int appleX;
	int appleY;

    private Stage stage;
    private Scene scene = new Scene(new Group(), SCREEN_WIDTH, SCREEN_HEIGHT);

    Game(App main){
        stage = main.getStage();
        primarySetup();
    }

    Game(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        primarySetup();
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
                switch (event.getCode()) {
                    case UP:    if(direction != 'D') {direction = 'U'; break;}
                    case DOWN:  if(direction != 'U') {direction = 'D'; break;}
                    case LEFT:  if(direction != 'R') {direction = 'L'; break;}
                    case RIGHT: if(direction != 'L') {direction = 'R'; break;}
                    default:
                        break;
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

    public void setup(){
        Group gameRoot = new Group();
        gameRoot = setGrid(gameRoot);
        scene.setRoot(gameRoot);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(long arg0) {
        double currentTime = System.nanoTime();
        if (FPS <= (currentTime - startTime)){
            setup();
        }    
    }
}
