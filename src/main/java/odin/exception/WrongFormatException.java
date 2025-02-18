package odin.exception;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Exception thrown when something does not follow the specified format.
 */
public class WrongFormatException extends OdinException {
    private Optional<String> correctFormat;

    /**
     * Default constructor.
     *
     * @param message Explanation of the exception.
     */
    public WrongFormatException(String message) {
        super(message);
        this.correctFormat = Optional.empty();
    }

    /**
     * Specify the correct format, which is to be presented to user as information.
     *
     * @param correctFormat The description of the correct format.
     */
    public void addCorrectFormat(String correctFormat) {
        this.correctFormat = Optional.of(correctFormat);
    }

    @Override
    public ArrayList<String> getMessageList() {
        ArrayList<String> messages = super.getMessageList();
        if (this.correctFormat.isPresent()) {
            messages.add(String.format("Usage: '%s'", this.correctFormat.orElse("")));
        }
        return messages;
    }
}
