package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.BuyDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CReceiptTest {

    private static final List<BuyDto> promotion = List.of(
            new BuyDto("제품1", 1000, 2, 2, 1),
            new BuyDto("제품2", 2000, 0, 3, 1));

    private static final List<BuyDto> noPromotion = List.of(
            new BuyDto("제품1", 1000, 2),
            new BuyDto("제품2", 2000, 3));

    private Receipt receipt;

    @BeforeEach
    void setUp() {
        receipt = new CReceipt();
    }

    @Test
    @DisplayName("프로모션 없는 경우 제품 설명 반환")
    void 프로모션_없는_경우_제품_설명_반환() {
        // given
        noPromotion.forEach(receipt::addProduct);

        // when
        String result = receipt.getProducts();

        // then
        assertThat(result.replaceAll("\\s","")).contains("제품122,000", "제품236,000");
    }

    @Test
    @DisplayName("프로모션 있는 경우 제품 설명 반환")
    void 프로모션_있는_경우_제품_설명_반환() {
        // given
        promotion.forEach(receipt::addProduct);

        // when
        String result = receipt.getProducts();

        // then
        assertThat(result.replaceAll("\\s","")).contains("제품144,000", "제품236,000");
    }

    @Test
    @DisplayName("프로모션 없는 경우 프로모션 설명 공백 반환")
    void 프로모션_없는_경우_프로모션_설명_공백_반환() {
        // given
        noPromotion.forEach(receipt::addProduct);

        // when
        String result = receipt.getPromotions();

        // then
        assertTrue(result.isBlank());
    }

    @Test
    @DisplayName("프로모션 있는 경우 프로모션 설명 반환")
    void 프로모션_있는_경우_프로모션_설명_반환() {
        // given
        promotion.forEach(receipt::addProduct);

        // when
        String result = receipt.getPromotions();

        // then
        assertThat(result.replaceAll("\\s","")).contains("제품11", "제품21");
    }

    @Test
    @DisplayName("프로모션 없는 경우 총액 반환")
    void 프로모션_없는_경우_총액_반환() {
        // given
        noPromotion.forEach(receipt::addProduct);

        // when
        String result = receipt.getTotal();
        
        // then
        assertThat(result.replaceAll("\\s","")).contains("총구매액58,000", "행사할인-0", "멤버십할인-0", "내실돈8,000");
    }

    @Test
    @DisplayName("프로모션 있는 경우 총액 반환")
    void 프로모션_있는_경우_총액_반환() {
        // given
        promotion.forEach(receipt::addProduct);

        // when
        String result = receipt.getTotal();

        // then
        assertThat(result.replaceAll("\\s","")).contains("총구매액710,000", "행사할인-3,000", "멤버십할인-0", "내실돈7,000");
    }

    @Test
    @DisplayName("프로모션 없고 멤버십 할인을 받는 경우 총액 반환")
    void 프로모션_없고_멤버십_할인을_받는_경우_총액_반환() {
        // given
        noPromotion.forEach(receipt::addProduct);
        boolean membership = true;

        // when
        receipt.applyMembership(membership);
        String result = receipt.getTotal();

        // then
        assertThat(result.replaceAll("\\s","")).contains("총구매액58,000", "행사할인-0", "멤버십할인-2,400", "내실돈5,600");
    }

    @Test
    @DisplayName("프로모션 있고 멤버십 할인을 받는 경우 총액 반환")
    void 프로모션_있고_멤버십_할인을_받는_경우_총액_반환() {
        // given
        promotion.forEach(receipt::addProduct);
        boolean membership = true;

        // when
        receipt.applyMembership(membership);
        String result = receipt.getTotal();

        // then
        assertThat(result.replaceAll("\\s","")).contains("총구매액710,000", "행사할인-3,000", "멤버십할인-600", "내실돈6,400");
    }
}