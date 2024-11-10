package store.model;

import store.util.StringUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class Convenience {
    private TreeMap<String, Product> products = new TreeMap<>();
    private TreeMap<String, Promotion> promotions = new TreeMap<>();

    public void init(List<String> promotionLines, List<String> productLines) {
        addPromotions(promotionLines);
        addProducts(productLines);
    }

    int getProductCount() {
        return products.size();
    }

    int getPromotionCount() {
        return promotions.size();
    }

    private void addPromotions(List<String> lines) {
        lines.forEach(this::addPromotion);
    }

    private void addPromotion(String line) {
        List<String> promotionDetail = StringUtil.splitFileString(line);

        promotions.put(promotionDetail.get(0),
                new CPromotion(
                        promotionDetail.get(0),
                        Integer.parseInt(promotionDetail.get(1)),
                        Integer.parseInt(promotionDetail.get(2)),
                        LocalDate.parse(promotionDetail.get(3)),
                        LocalDate.parse(promotionDetail.get(4))
                )
        );
    }

    private void addProducts(List<String> lines) {
        lines.forEach(this::addProduct);
    }

    private void addProduct(String line) {
        List<String> productDetail = StringUtil.splitFileString(line);

        Product product = getProduct(productDetail.get(0), Integer.parseInt(productDetail.get(1)));

        addQuantity(product, Integer.parseInt(productDetail.get(2)), productDetail.get(3));
    }

    private Product getProduct(String name, int price) {
        if (!products.containsKey(name)) {
            products.put(name, new CProduct(name, price));
        }
        return products.get(name);
    }

    private void addQuantity(Product product, int quantity, String promotionName) {
        if (promotionName.equals("null")) {
            product.addProduct(quantity);
            return;
        }
        product.addProduct(quantity, promotions.get(promotionName));
    }
}
