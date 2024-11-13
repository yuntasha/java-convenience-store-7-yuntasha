package store.dto;

import java.util.Objects;

public class ProductDetailDto {
    /**
     * 제품 이름
     */
    private final String name;
    /**
     * 제품 가격
     */
    private final int price;
    /**
     * 일반 제품 재고
     */
    private final int quantity;
    /**
     * 프로모션 이름
     */
    private final String promotionName;
    /**
     * 프로모션 제품 재고
     */
    private final int pQuantity;

    /**
     * 프로모션이 있는 경우 생성자
     *
     * @param name          재고 이름
     * @param price         재고 가격
     * @param quantity      재고 개수
     * @param promotionName 프로모션 이름
     * @param pQuantity     프로모션 재고 개수
     */
    public ProductDetailDto(String name, int price, int quantity, String promotionName, int pQuantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
        this.pQuantity = pQuantity;
    }

    /**
     * 프로모션이 없는 경우 생성자
     * 프로모션 이름 null, 프로모션 개수 0개
     *
     * @param name     재고 이름
     * @param price    재고 가격
     * @param quantity 재고 개수
     */
    public ProductDetailDto(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = null;
        this.pQuantity = 0;
    }

    /**
     * 제품 이름 반환
     *
     * @return 제품 이름 반환
     */
    public String getName() {
        return name;
    }

    /**
     * 제품 가격 반환
     *
     * @return 제품 가격 반환
     */
    public int getPrice() {
        return price;
    }

    /**
     * 일반 제품 재고 반환
     *
     * @return 일반 제품 재고
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 프로모션 이름 반환
     *
     * @return
     */
    public String getPromotionName() {
        return promotionName;
    }

    /**
     * 프로모션 제품 재고 반환
     *
     * @return 프로모션 제품 개수
     */
    public int getPQuantity() {
        return pQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailDto that = (ProductDetailDto) o;
        return price == that.price && quantity == that.quantity && pQuantity == that.pQuantity && Objects.equals(name, that.name) && Objects.equals(promotionName, that.promotionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, promotionName, pQuantity);
    }
}
