package store.view;

import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;

import java.util.List;

public interface OutputView {
    void welcome(List<ProductDetailDto> productDetailDto);

    void printErrorMessage(ConvenienceException e);

    void totalPrint(String allProduct, String promotionProduct, String total);
}
