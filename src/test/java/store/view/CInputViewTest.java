package store.view;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CInputViewTest {
    private static final List<String> success = List.of("[사이다-2]", "[사이다-2],[감자칩-1]", "[사이다-2],[감자칩-1],[콜라-3]");
    private static final List<String> fail = List.of("사이다-2", "[사이다-2][감자칩-1]", "[사이다-0],[감자칩a-1],[콜라-3]");

    @ParameterizedTest(name = "{0}은 구매 형식에 맞습니다.")
    @FieldSource("success")
    void buySuccess(String input) {
        // given
        CInputView cInputView = CInputView.getInstance();

        // when
        System.out.println(input);

        // then
        assertDoesNotThrow(() -> cInputView.validateBuyInput(input));
    }

    @ParameterizedTest(name = "{0}은 구매 형식이 아닙니다.")
    @FieldSource("fail")
    void buyFailure(String input) {
        // given
        CInputView cInputView = CInputView.getInstance();

        // when
        ConvenienceException e = assertThrows(ConvenienceException.class, () -> cInputView.validateBuyInput(input));

        // then
        assertEquals(ErrorMessage.WRONG_INPUT_FORMAT_ERROR.getMessage(), e.getMessage());
    }
}