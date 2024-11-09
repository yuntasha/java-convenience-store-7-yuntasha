package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.ProductDetailDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    @Test
    @DisplayName("재고 정보 반환 - 기간 중")
    void 재고_정보_반환_기간중() {
        // given
        Product product = new Product("콜라", 1000);
        product.addProduct(10);
        product.addProduct(11, new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30)));
        LocalDate now = LocalDate.of(2024, 11, 3);

        // when
        ProductDetailDto dto = product.getProductDetailDto(now);

        // then
        assertEquals(true,
                dto.equals(new ProductDetailDto("콜라", 1000, 10, "탄산2+1", 11)));
    }

    @Test
    @DisplayName("현재 재고 반환 - 기간 외")
    void 재고_정보_반환_기간외() {
        // given
        Product product = new Product("콜라", 1000);
        product.addProduct(10);
        product.addProduct(11, new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30)));
        LocalDate now = LocalDate.of(2023, 11, 3);

        // when
        ProductDetailDto dto = product.getProductDetailDto(now);

        // then
        assertEquals(true,
                dto.equals(new ProductDetailDto("콜라", 1000, 10)));
    }
}