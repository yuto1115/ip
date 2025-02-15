package odin.ui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import odin.Odin;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Odin odin;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image odinImage = new Image(this.getClass().getResourceAsStream("/images/Odin.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Odin instance */
    public void setOdin(Odin odin) {
        this.odin = odin;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Odin's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage));

        Pair<Boolean, ArrayList<String>> res = odin.handleUserInput(input);
        if (res.getKey()) {
            Platform.exit();
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < res.getValue().size(); i++) {
            if (i > 0) {
                output.append("\n");
            }
            output.append(res.getValue().get(i));
        }
        dialogContainer.getChildren().addAll(DialogBox.getSystemDialog(output.toString(), odinImage));
    }

    /**
     * Create a dialog box that contains Odin's welcome message.
     */
    public void printWelcomeMessage() {
        dialogContainer.getChildren().addAll(DialogBox.getSystemDialog(
                "I am Odin, god of wisdom.\nWhat knowledge do you seek?", odinImage));
    }

    /**
     * Create a dialog box that contains Odin's exit message.
     */
    public void printExitMessage() {
        dialogContainer.getChildren().addAll(DialogBox.getSystemDialog(
                "Bye. We shall meet again.", odinImage));
    }
}
