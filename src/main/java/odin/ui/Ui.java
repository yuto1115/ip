package odin.ui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import odin.Master;

/**
 * The class to interact with user through GUI.
 */
public class Ui extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private AnchorPane mainLayout;
    private Scene scene;
    private Stage stage;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image odinImage = new Image(this.getClass().getResourceAsStream("/images/Odin.png"));
    private Master master;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        master = new Master();
        makeGui();
        printWelcomeMessage();
    }

    @Override
    public void stop() {
        master.finish();
        printExitMessage();
    }

    private void makeGui() {
        setUpComponents();
        formatWindow();
        addEventHandlers();
    }

    private void setUpComponents() {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();
    }

    private void formatWindow() {
        stage.setTitle("Odin");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
    }

    private void addEventHandlers() {
        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());

        //Scroll down to the end every time dialogContainer's height changes
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage));

        Pair<Boolean, ArrayList<String>> res = master.handleUserInput(input);
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

    private void printWelcomeMessage() {
        dialogContainer.getChildren().addAll(DialogBox.getSystemDialog(
                "I am Odin, god of wisdom.\nWhat knowledge do you seek?", odinImage));
    }

    private void printExitMessage() {
        dialogContainer.getChildren().addAll(DialogBox.getSystemDialog(
                "Bye. We shall meet again.", odinImage));
    }
}
