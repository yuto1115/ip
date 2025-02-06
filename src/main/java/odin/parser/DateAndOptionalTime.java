package odin.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

import odin.exception.WrongFormatException;

/**
 * Class to represent date (mandatory) and time (optional).
 */
public class DateAndOptionalTime {
    private final LocalDate date;
    private final Optional<LocalTime> time;

    /**
     * Default constructor.
     *
     * @param tokens Tokens that represent date (& time).
     * @throws WrongFormatException If the given tokens do not follow the correct format.
     */
    public DateAndOptionalTime(ArrayList<String> tokens) throws WrongFormatException {
        if (tokens.size() != 1 && tokens.size() != 2) {
            throw new WrongFormatException("Date & time must contain one or two tokens.");
        }
        try {
            this.date = LocalDate.parse(tokens.get(0));
        } catch (DateTimeParseException e) {
            throw new WrongFormatException("Date cannot be parsed as yyyy-MM-dd.");
        }
        if (tokens.size() == 1) {
            this.time = Optional.empty();
        } else {
            try {
                this.time = Optional.of(LocalTime.parse(tokens.get(1)));
            } catch (DateTimeParseException e) {
                throw new WrongFormatException("Time cannot be parsed as hh:mm or hh:mm:ss.");
            }
        }
    }

    /**
     * Returns the formatted string that represents the date (& time).
     */
    @Override
    public String toString() {
        String res = date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        res += time.map(t -> ", " + t).orElse("");
        return res;
    }

    /**
     * Returns the original string (i.e., before being formatted) that represents the date (& time).
     */
    public String getOriginalString() {
        String res = date.toString();
        res += time.map(t -> " " + t).orElse("");
        return res;
    }
}
