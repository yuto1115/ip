import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

public class DateAndOptionalTime {
    private final LocalDate date;
    private final Optional<LocalTime> time;

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

    @Override
    public String toString() {
        String res = date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        res += time.map(t -> ", " + t).orElse("");
        return res;
    }

    public String getOriginalString() {
        String res = date.toString();
        res += time.map(t -> " " + t).orElse("");
        return res;
    }
}
