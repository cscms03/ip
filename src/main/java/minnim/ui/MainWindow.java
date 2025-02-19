package minnim.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import minnim.Minnim;
import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;

/**
 * The MainWindow class represents the primary UI layout of Minnim's JavaFX application.
 * It handles user interactions, displays messages, and integrates with the Minnim chatbot.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogueContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Minnim minnim;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/user.png"));
    private Image minnimImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));

    /**
     * Initializes the MainWindow by setting up UI elements and displaying a greeting message.
     * Binds the scroll pane's vertical value to the dialogue container's height,
     * ensuring that new messages remain in view.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogueContainer.heightProperty());
        sendButton.setFont(Font.font("Helvetica"));
        userInput.setFont(Font.font("Helvetica"));
        String greeting = "Hello! I'm Minnim.\nWhat can I do for you?";
        dialogueContainer.getChildren().add(DialogueBox.getMinnimDialogue(greeting, minnimImage));
    }

    /**
     * Sets the Minnim chatbot instance for this window.
     *
     * @param minnim The Minnim chatbot instance.
     */
    public void setMinnim(Minnim minnim) {
        this.minnim = minnim;
    }

    @FXML
    private void handleUserInput() throws MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        String input = userInput.getText();
        String response = minnim.getResponse(input);
        dialogueContainer.getChildren().addAll(
                DialogueBox.getUserDialogue(input, userImage),
                DialogueBox.getMinnimDialogue(response, minnimImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            Platform.exit();
        }
    }
}