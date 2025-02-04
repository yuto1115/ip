package odin.exception;

import java.util.ArrayList;
import java.util.Optional;

public class WrongFormatException extends OdinException {
    private Optional<String> correctFormat;

    public WrongFormatException(String message) {
        super(message);
        this.correctFormat = Optional.empty();
    }

    public void addCorrectFormat(String correctFormat) {
        this.correctFormat = Optional.of(correctFormat);
    }

    @Override
    public ArrayList<String> getMessageList() {
        ArrayList<String> messages = super.getMessageList();
        if (this.correctFormat.isPresent()) {
            messages.add(String.format("The correct method is to utter '%s'.", this.correctFormat.orElse("")));
        }
        return messages;
    }
}