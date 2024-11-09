package store.model;

public enum BuyState {
    /**
     * 정상 구매 가능
     */
    NOMAL,
    /**
     * 프로모션 상품 받을 수 있음
     */
    ADD_MORE,
    /**
     * 프로모션 상품 부족으로 일부 프로모션 구매 안됨
     */
    OUT_OF_STOCK
}
