package store.exception;

public class ConvenienceException extends IllegalArgumentException {

    private ErrorMessage errorMessage;

    public ConvenienceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public String getConsoleMessage() {
        return StringUtil.ERROR_PREFIX+errorMessage.getMessage();
    }
}
