package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.BuyDto;
import store.dto.BuyStateDto;
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
        BuyStateDto buyStateDto = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.NOMAL), buyStateDto);
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
        BuyStateDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.ADD_MORE), result);
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
        BuyStateDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.OUT_OF_STOCK, 3), result);
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
        BuyStateDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.NOMAL), result);
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
        BuyStateDto result = product.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.NOMAL), result);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션X")
    void 물건_구매_프로모션_X() {
        // given
        int quantity = 20;
        product.addProduct(quantity);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = product.buy(buyCount, now);

        // then
        assertEquals(new BuyDto("콜라", 1000, buyCount), result);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션 기간 외")
    void 물건_구매_프로모션_기간_외() {
        // given
        int quantity = 20;
        int pQuantity = 11;
        product.addProduct(quantity);
        product.addProduct(pQuantity, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = product.buy(buyCount, now);

        // then
        assertEquals(new BuyDto("콜라", 1000, buyCount), result);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션만 구매")
    void 물건_구매_프로모션_프로모션만_구매() {
        // given
        int quantity = 20;
        int pQuantity = 14;
        product.addProduct(quantity);
        product.addProduct(pQuantity, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = product.buy(buyCount, now);

        // then
        assertEquals(new BuyDto("콜라", 1000, 0, 12, 4), result);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션 일부 구매")
    void 물건_구매_프로모션_프로모션_일부_구매() {
        // given
        int quantity = 20;
        int pQuantity = 10;
        product.addProduct(quantity);
        product.addProduct(pQuantity, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = product.buy(buyCount, now);

        // then
        assertEquals(new BuyDto("콜라", 1000, 3, 9, 3), result);
    }


}