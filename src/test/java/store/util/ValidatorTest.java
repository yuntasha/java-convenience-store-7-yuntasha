package store.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private static final List<String> formatted = List.of("[제품-1000],[제품입-20000000]", "[제-10000]");
    private static final List<String> unFormatted = List.of(
            "[제품a-1000],[제품입-20000000]", "[제품1-1000],[제품입-20000000]",
            "[제품1-1000],[제품입-200000000]", "[제품1-1000,[제품입-200000000]",
            "[제품1-1000][제품입-200000000]", "[제품1-1000],");

    @ParameterizedTest(name = "{index} {0}은 형식에 맞음.")
    @FieldSource("formatted")
    void 입력_형식_정확한_경우() {
        // given
        String input = "[제품-1000],[제품입-20000000]";

        // when
        // then
        assertDoesNotThrow(() -> Validator.validateBuyInput(input));
    }

    @ParameterizedTest(name = "{index} {0}은 형식에 맞지 않음.")
    @FieldSource("unFormatted")
    void 입력_형식_부정확한_경우(String input) {
        // given

        // when
        ConvenienceException e = assertThrows(ConvenienceException.class, () -> Validator.validateBuyInput(input));

        // then
        assertEquals(ErrorMessage.WRONG_INPUT_FORMAT_ERROR.getMessage(), e.getMessage());
    }
}