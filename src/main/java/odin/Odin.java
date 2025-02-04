package odin;

import odin.exception.WrongFormatException;
import odin.parser.Parser;
import odin.task.TaskList;
import odin.ui.Ui;
import odin.storage.Storage;

import java.util.ArrayList;

import javafx.util.Pair;

public class Odin {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    public Odin(String recordFilePath) {
        this.ui = new Ui();
        this.storage = new Storage(recordFilePath);
        this.taskList = this.storage.load();
        this.parser = new Parser();
    }

    public void run() {
        this.ui.welcome();

        while (true) {
            ArrayList<String> tokens = this.ui.listen();
            try {
                Pair<Boolean, ArrayList<String>> result = this.parser.parseAndHandle(tokens, this.taskList);
                if (result.getKey()) {
                    break;
                }
                this.ui.speak(result.getValue());
            } catch (WrongFormatException e) {
                this.ui.speak(e.getMessageList());
            }
        }

        this.ui.exit();
        this.storage.save(this.taskList);
    }

    public static void main(String[] args) {
        new Odin("./src/data/records.txt").run();
    }
}
