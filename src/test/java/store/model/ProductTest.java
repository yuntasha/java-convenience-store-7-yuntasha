package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.BuyDto;
import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    private Product product;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        product = new Product("콜라", 1000);
        promotion = new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30));
    }

    @Test
    @DisplayName("재고 정보 반환 - 기간 중")
    void 재고_정보_반환_기간중() {
        // given
        product.addProduct(10);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);

        // when
        ProductDetailDto dto = product.getProductDetailDto(now);

        // then
        assertEquals(
                new ProductDetailDto("콜라", 1000, 10, "탄산2+1", 11),
                dto);
    }

    @Test
    @DisplayName("현재 재고 반환 - 기간 외")
    void 재고_정보_반환_기간외() {
        // given
        product.addProduct(10);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);

        // when
        ProductDetailDto dto = product.getProductDetailDto(now);

        // then
        assertEquals(new ProductDetailDto("콜라", 1000, 10),
                dto);
    }

    @Test
    @DisplayName("구매 가능 여부 - 구매 가능")
    void 구매_가능_여부_구매_가능() {
        // given
        product.addProduct(10);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 10;

        // when
        BuyDto buyDto = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyDto(BuyState.NOMAL), buyDto);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 없음) - 재고 부족")
    void 구매_가능_여부_재고_부족() {
        // given
        product.addProduct(10);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 11;

        // when
        ConvenienceException exception = assertThrows(ConvenienceException.class, () -> product.getBuyState(buyCount, now));

        // then
        assertEquals(ErrorMessage.EXCEEDED_QUANTITY_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 보유) - 재고 부족")
    void 구매_가능_여부_프로모션_보유_재고_부족() {
        // given
        product.addProduct(10);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 22;

        // when
        ConvenienceException exception = assertThrows(ConvenienceException.class, () -> product.getBuyState(buyCount, now));

        // then
        assertEquals(ErrorMessage.EXCEEDED_QUANTITY_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 보유) - 추가 프로모션")
    void 구매_가능_여부_프로모션_보유_추가_프로모션() {
        // given
        product.addProduct(10);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 8;

        // when
        BuyDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyDto(BuyState.ADD_MORE), result);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 보유) - 프로모션 부족")
    void 구매_가능_여부_프로모션_보유_프로모션_부족() {
        // given
        product.addProduct(10);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyDto(BuyState.OUT_OF_STOCK, 3), result);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 기간 외) - 재고 부족")
    void 구매_가능_여부_프로모션_기간_외_재고_부족() {
        // given
        product.addProduct(10);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 11;

        // when
        ConvenienceException exception = assertThrows(ConvenienceException.class, () -> product.getBuyState(buyCount, now));

        // then
        assertEquals(ErrorMessage.EXCEEDED_QUANTITY_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 기간 외) - 추가 프로모션 x")
    void 구매_가능_여부_프로모션_기간_외_추가_프로모션() {
        // given
        product.addProduct(20);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 8;

        // when
        BuyDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyDto(BuyState.NOMAL), result);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 기간 외) - 프로모션 부족 x")
    void 구매_가능_여부_프로모션_기간_외_프로모션_부족() {
        // given
        product.addProduct(20);
        product.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyDto(BuyState.NOMAL), result);
    }
}