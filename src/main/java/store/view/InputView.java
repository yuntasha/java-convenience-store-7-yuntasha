package store.view;

import store.dto.BuyInputDto;

import java.util.List;

public interface InputView {
    /**
     * 구매 입력 받기
     *
     * @return 구매 입력 데이터
     */
    List<BuyInputDto> getBuyInput();

    /**
     * 무료로 받을 수 있는 프로모션 상품 추가할 것인지 여부 질문 및 답
     *
     * @param name 제품 이름
     * @return 상품을 받으면 true
     */
    boolean isAdd(String name);

    /**
     * 멤버십 할인을 받을 것인지 질문 및 답
     *
     * @return 멤버십 할인을 받으면 true
     */
    boolean isRemove(String name, int count);

    /**
     * 멤버십 할인을 받을 것인지 질문 및 답
     *
     * @return 멤버십 할인을 받으면 true
     */
    boolean isMembership();

    /**
     * 상품을 추가로 구매할 것인지 여부
     *
     * @return 추가로 구매하면 true
     */
    boolean continueShopping();
}
