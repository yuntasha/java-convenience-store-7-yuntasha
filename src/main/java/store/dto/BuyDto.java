package store.dto;

import java.util.Objects;

public class BuyDto {
    /**
     * 제품 이름
     */
    private final String name;
    /**
     * 제품 가격
     */
    private final int price;
    /**
     * 프로모션 미적용 제품 구매 수량
     */
    private final int count;
    /**
     * 프로모션 제품 구매 수량(증정품 포함)
     */
    private final int pCount;
    /**
     * 프로모션 증정품 수량
     */
    private final int freeCount;

    /**
     * 프로모션이 적용되는 경우 사용
     *
     * @param name      제품 이름
     * @param price     제품 가격
     * @param count     일반 제품 개수
     * @param pCount    프로모션 제품 개수
     * @param freeCount 프로모션 증정품 개수
     */
    public BuyDto(String name, int price, int count, int pCount, int freeCount) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.pCount = pCount;
        this.freeCount = freeCount;
    }

    /**
     * 프로모션이 미적용되는 경우 사용
     *
     * @param name
     * @param price
     * @param count
     */
    public BuyDto(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.pCount = 0;
        this.freeCount = 0;
    }

    /**
     * 제품 이름 반환
     *
     * @return 제품 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 증정품 개수 반환
     *
     * @return 증정품 개수
     */
    public int getFreeCount() {
        return freeCount;
    }

    /**
     * 해당 상품 총 개수 반환
     *
     * @return 총 개수
     */
    public int getAllCount() {
        return count + pCount;
    }

    /**
     * 해당 제품 총 가격 반환
     *
     * @return 총 가격
     */
    public int getAllPrice() {
        return getAllCount() * price;
    }

    /**
     * 프로모션으로 할인되는 가격 반환
     *
     * @return 프로모션으로 할인되는 가격
     */
    public int getDiscount() {
        return freeCount * price;
    }

    /**
     * 프로모션 할인 적용 후 총 금액 반환
     *
     * @return 프로모션 할인 적용 후 총 금액
     */
    public int getNoPromotionPrice() {
        return count * price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyDto buyDto = (BuyDto) o;
        return price == buyDto.price && count == buyDto.count && pCount == buyDto.pCount && freeCount == buyDto.freeCount && Objects.equals(name, buyDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, count, pCount, freeCount);
    }
}
