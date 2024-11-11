package store.model;

import store.dto.BuyInputDto;
import store.dto.BuyStateDto;
import store.view.InputView;

public enum BuyState {
    /**
     * 정상 구매 가능
     */
    NOMAL {
        public void check(BuyInputDto buyInput, BuyStateDto buyState, InputView inputView) {
        }
    },
    /**
     * 프로모션 상품 받을 수 있음
     */
    ADD_MORE {
        public void check(BuyInputDto buyInput, BuyStateDto buyState, InputView inputView) {
            if (inputView.isAdd(buyInput.getName())) {
                buyInput.addCount();
            }
        }
    },
    /**
     * 프로모션 상품 부족으로 일부 프로모션 구매 안됨
     */
    OUT_OF_STOCK {
        public void check(BuyInputDto buyInput, BuyStateDto buyState, InputView inputView) {
            if (!inputView.isRemove(buyInput.getName(), buyState.getCount())) {
                buyInput.removeCount(buyState.getCount());
            }
        }
    };

    /**
     * 각 상태에 맞는 체크 함수
     *
     * @param buyInput  구매 입력 데이터
     * @param buyState  구매 상태 데이터
     * @param inputView 입력 도우미
     */
    public abstract void check(BuyInputDto buyInput, BuyStateDto buyState, InputView inputView);
}
