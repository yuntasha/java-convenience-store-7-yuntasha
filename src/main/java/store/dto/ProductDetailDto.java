package store.dto;

import java.util.Objects;

public class ProductDetailDto {
    private final String name;
    private final int price;
    private final int quantity;
    private final String promotionName;
    private final int pQuantity;

    /**
     * 프로모션이 있는 경우 생성자
     * @param name 재고 이름
     * @param price 재고 가격
     * @param quantity 재고 개수
     * @param promotionName 프로모션 이름
     * @param pQuantity 프로모션 재고 개수
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
     * @param name 재고 이름
     * @param price 재고 가격
     * @param quantity 재고 개수
     */
    public ProductDetailDto(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = null;
        this.pQuantity = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getpQuantity() {
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
