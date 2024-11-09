package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PromotionTest {
    @Test
    @DisplayName("프로모션 반환")
    void 프로모션_반환() {
        // given
        Promotion promotion = new Promotion("test", 1, 3,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30));

        // when
        String name = promotion.getName();

        // then
        assertEquals("test", name);
    }
}