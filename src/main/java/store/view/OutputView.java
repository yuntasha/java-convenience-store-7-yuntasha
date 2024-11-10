package store.view;

import store.dto.ProductDetailDto;

import java.util.List;

public interface OutputView {
    void welcome(List<ProductDetailDto> productDetailDto);
}
