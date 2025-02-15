package odin;

import java.util.ArrayList;

import javafx.util.Pair;
import odin.exception.WrongFormatException;
import odin.parser.Parser;
import odin.storage.Storage;
import odin.task.TaskList;

/**
 * Master class of the application.
 */
public class Master {
    private static final String RECORD_FILE_PATH = "./src/data/records.txt";
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    /**
     * Default constructor.
     */
    public Master() {
        this.storage = new Storage(RECORD_FILE_PATH);
        this.taskList = this.storage.load();
        this.parser = new Parser();
    }

    /**
     * Handle a new user input.
     *
     * @param input Input from the user.
     * @return Pair of boolean, indicating whether the conversation has finished,
     *         and messages to respond to the input.
     */
    public Pair<Boolean, ArrayList<String>> handleUserInput(String input) {
        ArrayList<String> tokens = tokenize(input);
        try {
            return this.parser.parseAndHandle(tokens, this.taskList);
        } catch (WrongFormatException e) {
            return new Pair<>(false, e.getMessageList());
        }
    }

    /**
     * Finish the application.
     */
    public void finish() {
        this.storage.save(this.taskList);
    }

    /**
     * Split by spaces the given string into tokens.
     *
     * @return List of tokens.
     */
    private ArrayList<String> tokenize(String str) {
        ArrayList<String> tokens = new ArrayList<>();
        for (String token : str.split(" ")) {
            if (!token.isEmpty()) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}
