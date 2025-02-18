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
 * Represents the main window of the Minnim application, which handles user input,
 * communicates with the Minnim backend to get responses, and displays the dialogue
 * between the user and Minnim.
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
    private Image userImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));
    private Image minnimImage = new Image(this.getClass().getResourceAsStream("/minnim.png"));

    /**
     * Initializes the main window with default settings and displays a greeting message
     * from Minnim when the application starts.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogueContainer.heightProperty());
        sendButton.setFont(Font.font("Helvetica"));
        userInput.setFont(Font.font("Helvetica"));
        assert minnimImage != null : "Minnim image must not be null";
        String greeting = "Hello! I'm Minnim.\nWhat can I do for you?";
        dialogueContainer.getChildren().add(DialogueBox.getMinnimDialogue(greeting, minnimImage));
    }

    /**
     * Sets the Minnim object that will be used for processing user input and generating responses.
     *
     * @param minnim The Minnim instance used to generate responses.
     */
    public void setMinnim(Minnim minnim) {
        assert minnim != null : "Minnim instance must not be null";
        this.minnim = minnim;
    }

    /**
     * Handles the user's input when the "Send" button is clicked.
     * It sends the user input to Minnim, retrieves a response,
     * and updates the dialogue container with the user's message and Minnim's response.
     * Exits the application if the user inputs "bye".
     *
     * @throws MinnimMissingTaskDetailException If the task description is missing.
     * @throws MinnimMissingDateException If the date information for a task is missing.
     * @throws MinnimTargetTaskNumNotFoundException If no task number is provided for task operations.
     * @throws MinnimNoTaskFoundException If the specified task number cannot be found.
     */
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