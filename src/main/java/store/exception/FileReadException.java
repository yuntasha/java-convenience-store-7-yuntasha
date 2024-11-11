package store.exception;

import store.util.StringUtil;

public class FileReadException extends IllegalArgumentException {
    /**
     * 파일 읽기 전용 커스텀 예외 생성
     * @param s 파일 이름
     */
    public FileReadException(String s) {
        super(StringUtil.makeFileErrorMessage(s));
    }
}
