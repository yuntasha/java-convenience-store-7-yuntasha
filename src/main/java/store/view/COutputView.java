package store.view;

import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;
import store.util.StringUtil;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class COutputView implements OutputView {

    private static OutputView cOutputView;
    private static final String HELLO_MESSAGE = "안녕하세요. W편의점입니다.\n" +
            "현재 보유하고 있는 상품입니다.\n";
    private static final String W_CONVENIENCE = "==============W 편의점================";
    private static final String PRODUCT_TITLE = "상품명\t\t수량\t금액";
    private static final String PROMOTION_TITLE = "=============증\t정===============";
    private static final String DELIMITER = "====================================";

    public static OutputView getInstance() {
        if (Objects.isNull(cOutputView)) {
            cOutputView = new COutputView();
        }
        return cOutputView;
    }

    /**
     * 환영 인사 및 재고 출력
     * <pre>
     * 안녕하세요. W편의점입니다.
     * 현재 보유하고 있는 상품입니다.
     *
     * - 콜라 1,000원 7개 탄산2+1
     * - 콜라 1,000원 10개
     * - 사이다 1,000원 8개 탄산2+1
     * - 사이다 1,000원 7개
     * </pre>
     * @param productDetailDto 재고 데이터
     */
    @Override
    public void welcome(List<ProductDetailDto> productDetailDto) {
        System.out.println(makeWelcome(productDetailDto));
    }

    /**
     * 에러 메세지 반환
     * @param e 에러
     */
    @Override
    public void printErrorMessage(ConvenienceException e) {
        System.out.println(e.getConsoleMessage());
    }

    /**
     * 영수증 출력
     * <pre>
     * ==============W 편의점================
     * 상품명		수량	금액
     * 콜라		10 	10,000
     * =============증	정===============
     * 콜라		2
     * ====================================
     * 총구매액		10	10,000
     * 행사할인			-2,000
     * 멤버십할인			-0
     * 내실돈			 8,000
     * </pre>
     * @param allProduct 총 구매 제품 출력 메세지
     * @param promotionProduct 프로모션 할인 출력 메세지
     * @param total 총 할인 적용 출력 메세지
     */
    @Override
    public void totalPrint(String allProduct, String promotionProduct, String total) {
        StringJoiner printText = new StringJoiner(StringUtil.LINE_BREAK);

        printText.add(W_CONVENIENCE);
        printText.add(PRODUCT_TITLE);
        printText.add(allProduct);
        printText.add(PROMOTION_TITLE);
        printText.add(promotionProduct);
        printText.add(DELIMITER);
        printText.add(total);

        System.out.println(printText.toString());
    }

    /**
     * 재고 출력 메세지 반환
     * <pre>
     * - 감자칩 1,500원 5개 반짝할인
     * - 감자칩 1,500원 5개
     * - 물 500원 10개
     * - 비타민워터 1,500원 6개
     * </pre>
     * @param productDetailDto 재고 데이터
     * @return 재고 출력 메세지
     */
    private String makeWelcome(List<ProductDetailDto> productDetailDto) {
        StringJoiner printText = new StringJoiner(StringUtil.LINE_BREAK);
        printText.add(HELLO_MESSAGE);

        productDetailDto.stream().map(StringUtil::dtoToString).forEach(printText::add);
        printText.add(StringUtil.EMPTY);
        return printText.toString();
    }
}
