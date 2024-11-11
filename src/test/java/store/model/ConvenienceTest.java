package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvenienceTest {
    @Test
    @DisplayName("프로모션과 재고 초기화")
    void 프로모션과_재고_초기화() {
        // given
        Convenience convenience = new Convenience();

        // when
        convenience.init(List.of("탄산2+1,2,1,2024-01-01,2024-12-31", "MD추천상품,1,1,2024-01-01,2024-12-31"),
                List.of("콜라,1000,10,탄산2+1", "콜라,1000,10,null", "사이다,1000,8,탄산2+1", "물,500,10,null"));
        int productCount = convenience.getProductCount();
        int promotionCount = convenience.getPromotionCount();

        // then
        assertEquals(3, productCount);
        assertEquals(2, promotionCount);
    }
}