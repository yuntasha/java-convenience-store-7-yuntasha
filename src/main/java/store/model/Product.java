package store.model;

import store.dto.ProductDetailDto;

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
     * @param now 현재 시각
     * @return 재고 정보 DTO
     */
    public ProductDetailDto getProductDetailDto(LocalDate now) {
        if (Objects.isNull(promotion) || !promotion.isPeriod(now)) {
            return new ProductDetailDto(name, price, quantity);
        }

        return new ProductDetailDto(name, price, quantity, promotion.getName(), pQuantity);
    }
}
