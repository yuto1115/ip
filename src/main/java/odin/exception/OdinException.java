package odin.exception;

import java.util.ArrayList;

/**
 * Exception class used in this application.
 */
public class OdinException extends Exception {
    /**
     * Default constructor.
     *
     * @param message Explanation of the exception.
     */
    OdinException(String message) {
        super(message);
    }

    /**
     * Returns a list of messages to be printed.
     */
    public ArrayList<String> getMessageList() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(this.getMessage());
        return messages;
    }
}
