package store.model;

import store.dto.BuyDto;
import store.dto.BuyInputDto;
import store.dto.BuyStateDto;
import store.dto.ProductDetailDto;
import store.exception.ConvenienceException;
import store.exception.ErrorMessage;
import store.util.StringUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class Convenience {
    /**
     * 편의점 제품
     * <pre>
     *     검색을 위해 Map으로 구성하였으며 보기 좋도록 사전순 정렬을 위해 TreeMap
     * </pre>
     */
    private TreeMap<String, Product> products = new TreeMap<>();
    /**
     * 편의점 프로모션
     * <pre>
     *     검색을 위해 Map으로 구성하였으며 보기 좋도록 사전순 정렬을 위해 TreeMap
     * </pre>
     */
    private TreeMap<String, Promotion> promotions = new TreeMap<>();

    /**
     * 프로모션과 제품 구성
     *
     * @param promotionLines 프로모션 정보
     * @param productLines   제품 정보
     */
    public void init(List<String> promotionLines, List<String> productLines) {
        addPromotions(promotionLines);
        addProducts(productLines);
    }

    /**
     * 제품 개수 반환
     * <pre>
     *     테스트를 위한 메서드
     * </pre>
     *
     * @return 제품 개수 반환
     */
    int getProductCount() {
        return products.size();
    }

    /**
     * 프로모션 개수 반환
     * <pre>
     *     테스트를 위한 메서드
     * </pre>
     *
     * @return 프로모션 개수 반환
     */
    int getPromotionCount() {
        return promotions.size();
    }

    /**
     * 프로모션 추가(복수)
     *
     * @param lines 프로모션 정보
     */
    private void addPromotions(List<String> lines) {
        lines.forEach(this::addPromotion);
    }

    /**
     * 프로모션 추가(단일)
     *
     * @param line 프로모션 정보
     */
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

    /**
     * 제품 추가(복수)
     *
     * @param lines 제품 정보
     */
    private void addProducts(List<String> lines) {
        lines.forEach(this::addProduct);
    }

    /**
     * 제품 추가(단일)
     *
     * @param line 제품 정보
     */
    private void addProduct(String line) {
        List<String> productDetail = StringUtil.splitFileString(line);

        Product product = getProduct(productDetail.get(0), Integer.parseInt(productDetail.get(1)));

        addQuantity(product, Integer.parseInt(productDetail.get(2)), productDetail.get(3));
    }

    /**
     * 제품 추가를 위한 검색
     * <pre>
     *     등록되지 않은 제품이라면 제품을 추가하고 객체 반환
     * </pre>
     *
     * @param name  제품 이름
     * @param price 제품 가격
     * @return 제품
     */
    private Product getProduct(String name, int price) {
        if (!products.containsKey(name)) {
            products.put(name, new CProduct(name, price));
        }
        return products.get(name);
    }

    /**
     * 제품 추가
     * <pre>
     *     프로모션 이름은 null일 경우 기본 제품으로 추가됨
     * </pre>
     *
     * @param product       제품
     * @param quantity      재고
     * @param promotionName 프로모션 이름
     */
    private void addQuantity(Product product, int quantity, String promotionName) {
        if (promotionName.equals("null")) {
            product.addProduct(quantity);
            return;
        }
        product.addProduct(quantity, promotions.get(promotionName));
    }

    /**
     * 재고 상세정보 반환
     *
     * @param now 현재 시각
     * @return 재고 상세정보 데이터 리스트
     */
    public List<ProductDetailDto> getProductDetails(LocalDate now) {
        return products.values().stream().map(p -> p.getProductDetailDto(now)).toList();
    }

    /**
     * 구매 상태 확인(정상 구매, 무료상품 가져올 수 있음, 일부 프로모션 불가)
     *
     * @param buyInput 구매 데이터
     * @param now      현재 시각
     * @return 구매 상태 반환
     * @throws ConvenienceException 재고 부족, 존재하지 않는 상품
     */
    public BuyStateDto getBuyState(BuyInputDto buyInput, LocalDate now) throws ConvenienceException {
        if (!products.containsKey(buyInput.getName())) {
            throw new ConvenienceException(ErrorMessage.NOT_EXIST_PRODUCT_ERROR);
        }
        return products.get(buyInput.getName()).getBuyState(buyInput.getCount(), now);
    }

    /**
     * 구매 후 재고 최신화
     *
     * @param buyInput 구매 데이터
     * @param now      현재 시각
     * @return 구매 개수 데이터 반환
     */
    public BuyDto buyProduct(BuyInputDto buyInput, LocalDate now) {
        return products.get(buyInput.getName()).buy(buyInput.getCount(), now);
    }
}
