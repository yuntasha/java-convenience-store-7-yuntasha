package store.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    static List<LocalDate> inDates = Arrays.asList(LocalDate.of(2024, 11, 1),
            LocalDate.of(2024, 11, 10),
            LocalDate.of(2024, 11, 30));

    static List<LocalDate> outDates = Arrays.asList(LocalDate.of(2024, 10, 30),
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2023, 11, 30));

    @ParameterizedTest(name = "{index} {0}은 기간 중에 있습니다.")
    @FieldSource("inDates")
    void inPeriod(LocalDate now) {
        // given
        LocalDate start = LocalDate.of(2024, 11, 1);
        LocalDate end = LocalDate.of(2024, 11, 30);
        
        // when
        boolean result = Validator.isPeriod(now, start, end);

        // then
        assertEquals(true, result);
    }

    @ParameterizedTest(name = "{index} {0}은 기간을 벗어났습니다.")
    @FieldSource("outDates")
    void outPeriod(LocalDate now) {
        // given
        LocalDate start = LocalDate.of(2024, 11, 1);
        LocalDate end = LocalDate.of(2024, 11, 30);

        // when
        boolean result = Validator.isPeriod(now, start, end);

        // then
        assertEquals(false, result);
    }
}