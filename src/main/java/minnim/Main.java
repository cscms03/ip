package minnim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import minnim.ui.MainWindow;


public class Main extends Application {


    // Ensure the directory exists before using it
    private String getFilePath() {
        String home = System.getProperty("user.home"); // Works on Windows, macOS, Linux
        Path dataPath = Paths.get(home, "Minnim"); // Saves in ~/Minnim (Linux/macOS) or C:\Users\YourUser\Minnim (Windows)
        Path filePath = dataPath.resolve("minnim.txt");

        if (!Files.exists(dataPath)) {
            try {
                Files.createDirectories(dataPath);
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
            }
        }
        return filePath.toString();
    }

    @Override
    public void start(Stage stage) {
        try {
            Minnim minnim = new Minnim(getFilePath());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMinnim(minnim);
            stage.setTitle("Minnim Task Manager");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}