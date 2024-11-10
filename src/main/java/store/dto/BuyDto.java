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

    public BuyDto(String name, int price, int count, int pCount, int freeCount) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.pCount = pCount;
        this.freeCount = freeCount;
    }

    public BuyDto(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.pCount = 0;
        this.freeCount = 0;
    }

    public String getName() {
        return name;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public int getAllCount() {
        return count + pCount;
    }

    public int getAllPrice() {
        return getAllCount() * price;
    }

    public int getDiscount() {
        return freeCount * price;
    }

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
