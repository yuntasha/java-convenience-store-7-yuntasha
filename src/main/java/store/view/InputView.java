package store.view;

import store.dto.BuyInputDto;
import store.dto.BuyStateDto;

import java.util.List;

public interface InputView {
    List<BuyInputDto> getBuyInput();
    boolean isAdd(String name);
    boolean isRemove(String name, int count);
    boolean isMembership();
    boolean continueShopping();
}
