package store.util;

import store.exception.ConvenienceException;
import store.exception.ErrorMessage;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {
    public static final String BUY_INPUT_FORMAT = "^\\[[가-힣]+-[1-9][0-9]*\\](,\\[[가-힣]+-[1-9][0-9]*\\])*$";

    /**
     * 입력 형식 유효성 검사
     * <pre>
     * [한글-숫자],[한글-숫자]...
     * </pre>
     *
     * @param input 입력
     * @throws ConvenienceException 형식에 맞지않음
     */
    public static void validateBuyInput(String input) throws ConvenienceException {
        if (!Pattern.matches(BUY_INPUT_FORMAT, input)) {
            throw new ConvenienceException(ErrorMessage.WRONG_INPUT_FORMAT_ERROR);
        }
    }
}
