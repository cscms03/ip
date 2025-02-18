package minnim;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

import minnim.ui.DialogueBox;
import minnim.ui.MainWindow;


public class Main extends Application {

    private Image userImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));
    private Image minnimImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Minnim minnim = new Minnim("data/minnim.Minnim.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMinnim(minnim);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}