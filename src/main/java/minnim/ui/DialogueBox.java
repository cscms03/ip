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
 * Represents a dialogue box consisting of text and an image.
 * Used in the UI to display user and Minnim's responses.
 */
public class DialogueBox extends HBox {

    @FXML
    private Label dialogue;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogueBox with the given text and image.
     *
     * @param text  The text content of the dialogue.
     * @param image The image associated with the dialogue (user or Minnim).
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

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialogue box for user messages.
     *
     * @param text  The text content of the user's message.
     * @param image The image representing the user.
     * @return A DialogueBox instance displaying the user's message.
     */
    public static DialogueBox getUserDialogue(String text, Image image) {
        return new DialogueBox(text, image);
    }

    /**
     * Creates a dialogue box for Minnim's responses and flips it
     * so that the image appears on the left.
     *
     * @param text The text content of Minnim's response.
     * @param img  The image representing Minnim.
     * @return A DialogueBox instance displaying Minnim's response.
     */
    public static DialogueBox getMinnimDialogue(String text, Image img) {
        DialogueBox dialogueBox = new DialogueBox(text, img);
        dialogueBox.flip();
        return dialogueBox;
    }
}