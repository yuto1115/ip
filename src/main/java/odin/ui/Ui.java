package odin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to handle interaction with user.
 */
public class Ui {
    private static final String SEPARATOR = "_________________________________________________________________________________________________________";
    private final Scanner scanner;

    /**
     * Default constructor.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints a message to welcome user.
     */
    public void welcome() {
        speak("I am Odin, god of wisdom.", "What knowledge do you seek?");
    }

    /**
     * Prints a message to finish conversation.
     */
    public void exit() {
        speak("Bye. We shall meet again.");
    }

    /**
     * Prints messages in a specific format.
     */
    public void speak(ArrayList<String> messages) {
        for (int i = 0; i < messages.size(); i++) {
            if (i == 0) {
                System.out.println("Odin: " + messages.get(i));
            } else {
                System.out.println("      " + messages.get(i));
            }
        }
        System.out.println(SEPARATOR);
    }

    public void speak(String... messages) {
        speak(new ArrayList<>(List.of(messages)));
    }

    /**
     * Reads input from the user, and splits it by spaces into tokens.
     *
     * @return The list of tokens the user inputted.
     */
    public ArrayList<String> listen() {
        ArrayList<String> tokens = new ArrayList<>();
        while (tokens.isEmpty()) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            for (String token : input.split(" ")) {
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
            }
        }
        System.out.println(SEPARATOR);
        return tokens;
    }
}
