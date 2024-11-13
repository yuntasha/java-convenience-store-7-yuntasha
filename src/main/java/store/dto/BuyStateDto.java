package store.dto;

import store.model.BuyState;

import java.util.Objects;

public class BuyStateDto {
    /**
     * 구매 상태
     * <pre>
     *     제품을 무료로 받을 수 있는 상태, 일부 제품이 프로모션 미적용 상태, 기본
     * </pre>
     */
    private final BuyState buyState;
    /**
     * 프로모션 미적용 제품 개수
     */
    private final int count;

    /**
     * 일부 제품이 프로모션 미적용인 경우
     *
     * @param buyState 구매 상태
     * @param count    미적용된 제품 개수
     */
    public BuyStateDto(BuyState buyState, int count) {
        this.buyState = buyState;
        this.count = count;
    }

    /**
     * 보통 혹은 추가 구매의 경우
     *
     * @param buyState 구매 상태
     */
    public BuyStateDto(BuyState buyState) {
        this.buyState = buyState;
        this.count = 0;
    }

    /**
     * 구매 상태 반환
     *
     * @return 구매 상태
     */
    public BuyState getBuyState() {
        return buyState;
    }

    /**
     * 프로모션 미적용된 제품 개수 반환
     *
     * @return 프로모션 미적용된 제품 개수
     */
    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyStateDto buyStateDto = (BuyStateDto) o;
        return count == buyStateDto.count && buyState == buyStateDto.buyState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyState, count);
    }
}
