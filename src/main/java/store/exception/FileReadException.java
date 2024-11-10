package store.exception;

import store.util.StringUtil;

public class FileReadException extends IllegalArgumentException {
    public FileReadException(String s) {
        super(StringUtil.makeFileErrorMessage(s));
    }
}
