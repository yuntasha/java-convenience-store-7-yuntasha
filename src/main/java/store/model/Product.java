package store.model;

import store.dto.BuyDto;
import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;

import java.time.LocalDate;
import java.util.Objects;

public class Product {

    private String name;
    private int price;
    private int quantity = 0;
    private Promotion promotion = null;
    private int pQuantity = 0;

    /**
     * 재고 종류 생성
     *
     * @param name  재고 이름
     * @param price 재고 가격
     */
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * 프로모션 재고 추가
     *
     * @param pQuantity 추가 재고 개수
     * @param promotion 프로모션
     */
    public void addProduct(int pQuantity, Promotion promotion) {
        this.pQuantity += pQuantity;
        this.promotion = promotion;
    }

    /**
     * 기본 재고 추가
     *
     * @param quantity 추가 재고 개수
     */
    public void addProduct(int quantity) {
        this.quantity += quantity;
    }

    /**
     * 재고 정보 반환
     * 프로모션 기간이 아닌 경우 프로모션 재고와 프로모션 재공하지 않음
     *
     * @param now 현재 시각
     * @return 재고 정보 DTO
     */
    public ProductDetailDto getProductDetailDto(LocalDate now) {
        if (isPromotion(now)) {
            return new ProductDetailDto(name, price, quantity, promotion.getName(), pQuantity);
        }

        return new ProductDetailDto(name, price, quantity);
    }

    /**
     * 구매 상태(구매 가능, 프로모션 추가 가능, 일부 프로모션 불가, 재고 부족) 반환
     *
     * @param buyCount 구매 개수
     * @param now      현재 시각
     * @return 구매 상태 반환
     * @throws ConvenienceException 재고 부족 예외
     */
    public BuyDto getBuyState(int buyCount, LocalDate now) throws ConvenienceException {
        if (!isEnoughQuantity(buyCount, now)) {
            throw new ConvenienceException(ErrorMessage.EXCEEDED_QUANTITY_ERROR);
        }

        if (isNotEnoughPromotionQuantity(buyCount, now)) {
            return new BuyDto(BuyState.OUT_OF_STOCK, getNomalBuyCount(buyCount));
        }

        if (isPromotion(now) && promotion.giveMore(buyCount, now)) {
            return new BuyDto(BuyState.ADD_MORE);
        }

        return new BuyDto(BuyState.NOMAL);
    }

    /**
     * 프로모션 유무 포함 물품을 해당 개수만큼 구매 가능 여부
     */
    private boolean isEnoughQuantity(int buyCount, LocalDate now) {
        if (isPromotion(now)) {
            return buyCount <= quantity + pQuantity;
        }
        return buyCount <= quantity;
    }

    /**
     * 현재 적용되는 프로모션이 있는지 여부
     *
     * @param now 현재 시간
     * @return 프로모션 적용 : true, 프로모션 미적용 : false
     */
    private boolean isPromotion(LocalDate now) {
        return !Objects.isNull(promotion) && promotion.isPeriod(now);
    }

    /**
     * 프로모션이 적용 안되는 재고가 있는지 여부
     *
     * @param buyCount 구매 개수
     * @return 프로모션 적용 안되는 재고가 있으면 true
     */
    private boolean isNotEnoughPromotionQuantity(int buyCount, LocalDate now) {
        if (isPromotion(now)) {
            return buyCount > pQuantity && getNomalBuyCount(buyCount) >= promotion.getBuyPlusGet();
        }
        return false;
    }

    /**
     * 프로모션으로 못 사는 제품 개수 반환
     *
     * @param buyCount 구매 개수
     * @return 일반 구매 개수
     */
    private int getNomalBuyCount(int buyCount) {
        return buyCount - pQuantity + (pQuantity % promotion.getBuyPlusGet());
    }
}
