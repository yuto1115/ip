import javafx.application.Application;
import odin.ui.Ui;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Ui.class, args);
    }
}
