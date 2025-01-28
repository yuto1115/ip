public class WrongFormatException extends OdinException {
    public WrongFormatException(String correctFormat) {
        super(String.format("Your format is flawed. The correct method is to utter '%s'.", correctFormat));
    }
}