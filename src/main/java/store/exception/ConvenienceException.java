package store.exception;

import store.util.StringUtil;

public class ConvenienceException extends IllegalArgumentException{

    private ErrorMessage errorMessage;

    public ConvenienceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public String getConsoleMessage() {
        return StringUtil.ERROR_PREFIX+errorMessage.getMessage();
    }
}
