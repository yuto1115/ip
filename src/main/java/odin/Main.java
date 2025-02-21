package odin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import odin.ui.MainWindow;

/**
 * The entry point of the GUI application.
 */
public class Main extends Application {
    private MainWindow mainWindow;
    private Odin odin;

    @Override
    public void start(Stage stage) {
        this.odin = new Odin();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Odin (chatbot)");
            stage.show();
            this.mainWindow = fxmlLoader.<MainWindow>getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainWindow.setOdin(this.odin);
        this.mainWindow.printWelcomeMessage();
    }

    @Override
    public void stop() {
        odin.finish();
        this.mainWindow.printExitMessage();
    }
}
