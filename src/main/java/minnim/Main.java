package minnim;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import minnim.ui.MainWindow;

/**
 * The entry point of the Minnim application. This class initializes and launches the JavaFX
 * application window, sets up the user interface, and integrates the Minnim chatbot functionality.
 */
public class Main extends Application {

    private Image userImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));
    private Image minnimImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));
    private Minnim minnim = new Minnim("data/minnim.Minnim.txt");

    /**
     * The main entry point for launching the JavaFX application.
     *
     * @param stage The primary stage for the application, where the scene is set.
     */
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