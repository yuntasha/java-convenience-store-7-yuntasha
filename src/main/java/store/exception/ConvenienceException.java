package store.exception;

public class ConvenienceException extends IllegalArgumentException {

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final ErrorMessage errorMessage;

    public ConvenienceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public String getConsoleMessage() {
        return ERROR_PREFIX + errorMessage.getMessage();
    }
}
