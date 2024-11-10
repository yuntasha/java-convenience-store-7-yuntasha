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

class CProductTest {

    private CProduct CProduct;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        CProduct = new CProduct("콜라", 1000);
        promotion = new CPromotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30));
    }

    @Test
    @DisplayName("재고 정보 반환 - 기간 중")
    void 재고_정보_반환_기간중() {
        // given
        CProduct.addProduct(10);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);

        // when
        ProductDetailDto dto = CProduct.getProductDetailDto(now);

        // then
        assertEquals(
                new ProductDetailDto("콜라", 1000, 10, "탄산2+1", 11),
                dto);
    }

    @Test
    @DisplayName("현재 재고 반환 - 기간 외")
    void 재고_정보_반환_기간외() {
        // given
        CProduct.addProduct(10);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);

        // when
        ProductDetailDto dto = CProduct.getProductDetailDto(now);

        // then
        assertEquals(new ProductDetailDto("콜라", 1000, 10),
                dto);
    }

    @Test
    @DisplayName("구매 가능 여부 - 구매 가능")
    void 구매_가능_여부_구매_가능() {
        // given
        CProduct.addProduct(10);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 10;

        // when
        BuyStateDto buyStateDto = CProduct.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.NOMAL), buyStateDto);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 없음) - 재고 부족")
    void 구매_가능_여부_재고_부족() {
        // given
        CProduct.addProduct(10);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 11;

        // when
        ConvenienceException exception = assertThrows(ConvenienceException.class, () -> CProduct.getBuyState(buyCount, now));

        // then
        assertEquals(ErrorMessage.EXCEEDED_QUANTITY_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 보유) - 재고 부족")
    void 구매_가능_여부_프로모션_보유_재고_부족() {
        // given
        CProduct.addProduct(10);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 22;

        // when
        ConvenienceException exception = assertThrows(ConvenienceException.class, () -> CProduct.getBuyState(buyCount, now));

        // then
        assertEquals(ErrorMessage.EXCEEDED_QUANTITY_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 보유) - 추가 프로모션")
    void 구매_가능_여부_프로모션_보유_추가_프로모션() {
        // given
        CProduct.addProduct(10);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 8;

        // when
        BuyStateDto result = CProduct.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.ADD_MORE), result);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 보유) - 프로모션 부족")
    void 구매_가능_여부_프로모션_보유_프로모션_부족() {
        // given
        CProduct.addProduct(10);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyStateDto result = CProduct.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.OUT_OF_STOCK, 3), result);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 기간 외) - 재고 부족")
    void 구매_가능_여부_프로모션_기간_외_재고_부족() {
        // given
        CProduct.addProduct(10);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 11;

        // when
        ConvenienceException exception = assertThrows(ConvenienceException.class, () -> CProduct.getBuyState(buyCount, now));

        // then
        assertEquals(ErrorMessage.EXCEEDED_QUANTITY_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 기간 외) - 추가 프로모션 x")
    void 구매_가능_여부_프로모션_기간_외_추가_프로모션() {
        // given
        CProduct.addProduct(20);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 8;

        // when
        BuyStateDto result = CProduct.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.NOMAL), result);
    }

    @Test
    @DisplayName("구매 가능 여부(프로모션 기간 외) - 프로모션 부족 x")
    void 구매_가능_여부_프로모션_기간_외_프로모션_부족() {
        // given
        CProduct.addProduct(20);
        CProduct.addProduct(11, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 12;

        // when
        BuyStateDto result = CProduct.getBuyState(buyCount, now);

        // then
        assertEquals(new BuyStateDto(BuyState.NOMAL), result);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션X")
    void 물건_구매_프로모션_X() {
        // given
        int quantity = 20;
        CProduct.addProduct(quantity);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = CProduct.buy(buyCount, now);
        ProductDetailDto detailDto = CProduct.getProductDetailDto(now);

        // then
        assertEquals(new BuyDto("콜라", 1000, buyCount), result);
        assertEquals(new ProductDetailDto("콜라", 1000, 8), detailDto);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션 기간 외")
    void 물건_구매_프로모션_기간_외() {
        // given
        int quantity = 20;
        int pQuantity = 11;
        CProduct.addProduct(quantity);
        CProduct.addProduct(pQuantity, promotion);
        LocalDate now = LocalDate.of(2023, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = CProduct.buy(buyCount, now);
        ProductDetailDto detailDto = CProduct.getProductDetailDto(now);

        // then
        assertEquals(new BuyDto("콜라", 1000, buyCount), result);
        assertEquals(new ProductDetailDto("콜라", 1000, 8), detailDto);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션만 구매")
    void 물건_구매_프로모션_프로모션만_구매() {
        // given
        int quantity = 20;
        int pQuantity = 14;
        CProduct.addProduct(quantity);
        CProduct.addProduct(pQuantity, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = CProduct.buy(buyCount, now);
        ProductDetailDto detailDto = CProduct.getProductDetailDto(now);

        // then
        assertEquals(new BuyDto("콜라", 1000, 0, 12, 4), result);
        assertEquals(new ProductDetailDto("콜라", 1000, 20, promotion.getName(), 2), detailDto);
    }

    @Test
    @DisplayName("물건 구매 - 프로모션 일부 구매")
    void 물건_구매_프로모션_프로모션_일부_구매() {
        // given
        int quantity = 20;
        int pQuantity = 10;
        CProduct.addProduct(quantity);
        CProduct.addProduct(pQuantity, promotion);
        LocalDate now = LocalDate.of(2024, 11, 3);
        int buyCount = 12;

        // when
        BuyDto result = CProduct.buy(buyCount, now);
        ProductDetailDto detailDto = CProduct.getProductDetailDto(now);

        // then
        assertEquals(new BuyDto("콜라", 1000, 3, 9, 3), result);
        assertEquals(new ProductDetailDto("콜라", 1000, 18, promotion.getName(), 0), detailDto);
    }
}