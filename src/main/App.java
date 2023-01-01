package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;


/*
 * JavaFX App
*/
public class App extends Application {

    // screen setup
    private Stage stage = new Stage();
    private Scene scene;

    public Stage getStage() {
        return stage;
    }

    private void setStage(){
        // some stage usefuls methodes :
        Image icon = new Image("resources/assets/icon.png");
        stage.getIcons().add(icon);

        stage.setTitle("Snake YEAAAAAH");
        stage.setResizable(false);
        /*
        * stage.setWidth(420);
        * stage.setHeight(420); 
        * stage.setX(10); stage.setY(10)
        * stage.setFullScreen(true)
        * stage.setFullScreenExitHint("You can escape. or maybe not ? Anyway try escape or idk maybe 'q'");
        * stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        */
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set the first menu
        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        setStage();

        // setGameScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
