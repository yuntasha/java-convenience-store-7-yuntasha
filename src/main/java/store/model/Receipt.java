package store.model;

import store.dto.BuyDto;

public interface Receipt {
    void addProduct(BuyDto buyDto);

    void applyMembership(boolean b);

    String getProducts();

    String getPromotions();

    String getTotal();
}
