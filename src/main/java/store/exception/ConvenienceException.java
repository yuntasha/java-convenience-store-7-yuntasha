package store.exception;

public class ConvenienceException extends IllegalArgumentException {

    /**
     * 콘솔 에러 메세지 접두사
     */
    private static final String ERROR_PREFIX = "[ERROR] ";

    /**
     * 에러 메세지
     */
    private final ErrorMessage errorMessage;

    /**
     * 편의점 커스텀 예외 생성
     *
     * @param errorMessage 에러 메세지
     */
    public ConvenienceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    /**
     * 콘솔용 에러메세지 반환
     *
     * @return 콘솔용 에러메세지
     */
    public String getConsoleMessage() {
        return ERROR_PREFIX + errorMessage.getMessage();
    }
}
