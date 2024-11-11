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

    public static OutputView getInstance() {
        if (Objects.isNull(cOutputView)) {
            cOutputView = new COutputView();
        }
        return cOutputView;
    }

    @Override
    public void welcome(List<ProductDetailDto> productDetailDto) {
        System.out.println(makeWelcome(productDetailDto));
    }

    @Override
    public void printErrorMessage(ConvenienceException e) {
        System.out.println(e.getConsoleMessage());
    }

    @Override
    public void totalPrint(String allProduct, String promotionProduct, String total) {
        StringJoiner printText = new StringJoiner(StringUtil.LINE_BREAK);

        printText.add(W_CONVENIENCE);
        printText.add(StringUtil.PRODUCT_TITLE);
        printText.add(allProduct);
        printText.add(StringUtil.PROMOTION_TITLE);
        printText.add(promotionProduct);
        printText.add(StringUtil.DELIMITER);
        printText.add(total);

        System.out.println(printText.toString());
    }

    private String makeWelcome(List<ProductDetailDto> productDetailDto) {
        StringJoiner printText = new StringJoiner(StringUtil.LINE_BREAK);
        printText.add(HELLO_MESSAGE);

        productDetailDto.stream().map(StringUtil::dtoToString).forEach(printText::add);
        printText.add(StringUtil.EMPTY);
        return printText.toString();
    }
}
