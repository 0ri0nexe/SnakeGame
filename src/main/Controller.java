package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Controller {

    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;

    private Stage stage;
    private Scene scene;

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

    public void setGame(ActionEvent e){
        Group gameRoot = new Group();
        gameRoot = setGrid(gameRoot);

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(gameRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void setGameOver(ActionEvent e){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gameOverMenu.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}