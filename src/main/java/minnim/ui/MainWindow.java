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

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogueContainer.heightProperty());
        sendButton.setFont(Font.font("Helvetica"));
        userInput.setFont(Font.font("Helvetica"));
        String greeting = "Hello! I'm Minnim.\nWhat can I do for you?";
        dialogueContainer.getChildren().add(DialogueBox.getMinnimDialogue(greeting, minnimImage));
    }

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