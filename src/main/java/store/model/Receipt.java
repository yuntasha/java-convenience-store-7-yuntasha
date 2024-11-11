package store.model;

import store.dto.BuyDto;

public interface Receipt {
    /**
     * 구매할 제품 추가
     *
     * @param buyDto 구매 정보
     */
    void addProduct(BuyDto buyDto);

    /**
     * 멤버십 적용
     *
     * @param b 멤버십 적용 여부
     */
    void applyMembership(boolean b);

    /**
     * 영수증의 구매한 제품 문자열 반환
     *
     * @return 영수증의 구매한 제품 부분
     */
    String getProducts();

    /**
     * 행사로 무료로 얻은 품목 문자열 반환
     *
     * @return 영수증의 행사로 무료로 얻은 품목
     */
    String getPromotions();

    /**
     * 금액 정보 반환
     *
     * @return 출력 형식에 맞춰 반환
     */
    String getTotal();
}
