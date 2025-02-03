import java.util.ArrayList;

public class OdinException extends Exception {
    OdinException(String message) {
        super(message);
    }

    public ArrayList<String> getMessageList() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(this.getMessage());
        return messages;
    }
};