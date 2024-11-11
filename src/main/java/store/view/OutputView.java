package store.view;

import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;

import java.util.List;

public interface OutputView {
    /**
     * 환영 인사 및 재고 출력
     *
     * @param productDetailDto 재고 데이터
     */
    void welcome(List<ProductDetailDto> productDetailDto);

    /**
     * 에러 메세지 반환
     *
     * @param e 에러
     */
    void printErrorMessage(ConvenienceException e);

    /**
     * @param allProduct       총 구매 제품 출력 메세지
     * @param promotionProduct 프로모션 할인 출력 메세지
     * @param total            총 할인 적용 출력 메세지
     */
    void totalPrint(String allProduct, String promotionProduct, String total);
}
