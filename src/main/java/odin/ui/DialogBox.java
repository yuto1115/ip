package odin.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A container node that places a text and an image horizontally
 */
class DialogBox extends HBox {
    private DialogBox(String s, Image i) {
        Label text = new Label(s);
        ImageView displayPicture = new ImageView(i);

        //Styling the dialog box
        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Create a user dialog box.
     *
     * @param s Text to show.
     * @param i Image to show.
     */
    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    /**
     * Create a system dialog box.
     *
     * @param s Text to show.
     * @param i Image to show.
     */
    public static DialogBox getSystemDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        db.flip();
        return db;
    }
}
