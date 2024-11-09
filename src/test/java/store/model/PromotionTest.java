package store.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import store.util.Validator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PromotionTest {

    private static final List<LocalDate> inDates = Arrays.asList(LocalDate.of(2024, 11, 1),
            LocalDate.of(2024, 11, 10),
            LocalDate.of(2024, 11, 30));

    private static final List<LocalDate> outDates = Arrays.asList(LocalDate.of(2024, 10, 30),
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2023, 11, 30));

    private Promotion promotion;


    @BeforeEach
    void setUp() {
        promotion = new Promotion("test", 1, 3,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30));
    }

    @Test
    @DisplayName("프로모션 반환")
    void 프로모션_반환() {
        // given

        // when
        String name = promotion.getName();

        // then
        assertEquals("test", name);
    }


    @ParameterizedTest(name = "{index} {0}은 기간 중에 있습니다.")
    @FieldSource("inDates")
    void inPeriod(LocalDate now) {
        // given

        // when
        boolean result = promotion.isPeriod(now);

        // then
        assertEquals(true, result);
    }

    @ParameterizedTest(name = "{index} {0}은 기간을 벗어났습니다.")
    @FieldSource("outDates")
    void outPeriod(LocalDate now) {
        // given

        // when
        boolean result = promotion.isPeriod(now);

        // then
        assertEquals(false, result);
    }
}