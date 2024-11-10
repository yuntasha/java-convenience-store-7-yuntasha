package store.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.ProductDetailDto;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {
    @Test
    @DisplayName("상품 재고 메세지 확인")
    void 상품_재고_메세지_확인() {
        // given
        ProductDetailDto dto1 = new ProductDetailDto("콜라", 1000, 10);
        ProductDetailDto dto2 = new ProductDetailDto("콜라", 1000, 10, "프로모션", 10);
        ProductDetailDto dto3 = new ProductDetailDto("콜라", 1000, 0, "프로모션", 10);

        // when
        String message1 = StringUtil.dtoToString(dto1);
        String message2 = StringUtil.dtoToString(dto2);
        String message3 = StringUtil.dtoToString(dto3);

        // then
        assertEquals("- 콜라 1,000원 10개", message1);
        assertEquals("- 콜라 1,000원 10개 프로모션\n- 콜라 1,000원 10개", message2);
        assertEquals("- 콜라 1,000원 10개 프로모션\n- 콜라 1,000원 재고 없음", message3);
    }
}