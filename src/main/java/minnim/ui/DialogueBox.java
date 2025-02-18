package minnim.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Represents a dialogue box that displays text and an image (profile picture).
 * The dialogue box is used to display messages from either the user or Minnim.
 */
public class DialogueBox extends HBox {

    @FXML
    private Label dialogue;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogueBox with the specified text and profile picture.
     * The DialogueBox layout is loaded from the FXML file.
     *
     * @param text The text to be displayed in the dialogue box.
     * @param image The profile picture to be displayed in the dialogue box.
     */
    public DialogueBox(String text, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogueBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogue.setText(text);
        dialogue.setFont(Font.font("Helvetica"));
        displayPicture.setImage(image);
    }

    /**
     * Flips the position of the dialogue box to change its alignment.
     * Used for flipping the dialogue box when Minnim is speaking (to the left side).
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialogue box for the user with the specified text and profile picture.
     *
     * @param text The text to be displayed in the user dialogue box.
     * @param image The profile picture to be displayed in the user dialogue box.
     * @return A DialogueBox instance representing the user’s message.
     */
    public static DialogueBox getUserDialogue(String text, Image image) {
        return new DialogueBox(text, image);
    }

    /**
     * Creates a dialogue box for Minnim with the specified text and profile picture.
     * The dialogue box is flipped to position it on the left side for Minnim's messages.
     *
     * @param text The text to be displayed in the Minnim dialogue box.
     * @param img The profile picture to be displayed in the Minnim dialogue box.
     * @return A DialogueBox instance representing Minnim's message, flipped to the left side.
     */
    public static DialogueBox getMinnimDialogue(String text, Image img) {
        DialogueBox dialogueBox = new DialogueBox(text, img);
        dialogueBox.flip();
        return dialogueBox;
    }
}