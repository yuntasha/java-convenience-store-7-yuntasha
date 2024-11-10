package store.view;

import store.dto.ProductDetailDto;
import store.util.StringUtil;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class COutputView implements OutputView {

    private static OutputView cOutputView;

    public OutputView getInstance() {
        if (Objects.isNull(cOutputView)) {
            cOutputView = new COutputView();
        }
        return cOutputView;
    }

    @Override
    public void welcome(List<ProductDetailDto> productDetailDto) {
        System.out.println(makeWelcome(productDetailDto));
    }

    private String makeWelcome(List<ProductDetailDto> productDetailDto) {
        StringJoiner printText = new StringJoiner(StringUtil.LINE_BREAK);
        printText.add(StringUtil.HELLO_MESSAGE);

        productDetailDto.stream().map(StringUtil::dtoToString).forEach(printText::add);

        return printText.toString();
    }
}
