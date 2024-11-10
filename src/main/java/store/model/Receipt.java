package store.model;

import store.dto.BuyDto;

public interface Receipt {
    void addProduct(BuyDto buyDto);
    void applyMembership();
    String getProducts();
    String getPromotions();
    String getTotal();
}
