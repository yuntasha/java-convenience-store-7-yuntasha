package store.view;

import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;
import store.util.StringUtil;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class COutputView implements OutputView {

    private static OutputView cOutputView;

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

        printText.add(StringUtil.W_CONVENIENCE);
        printText.add(allProduct);
        printText.add(StringUtil.PROMOTION_TITLE);
        printText.add(promotionProduct);
        printText.add(StringUtil.DELIMITER);
        printText.add(total);

        System.out.println(printText.toString());
    }

    private String makeWelcome(List<ProductDetailDto> productDetailDto) {
        StringJoiner printText = new StringJoiner(StringUtil.LINE_BREAK);
        printText.add(StringUtil.HELLO_MESSAGE);

        productDetailDto.stream().map(StringUtil::dtoToString).forEach(printText::add);
        printText.add(StringUtil.EMPTY);
        return printText.toString();
    }
}
