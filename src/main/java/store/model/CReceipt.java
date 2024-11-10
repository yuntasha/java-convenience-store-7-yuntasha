package store.model;

import store.dto.BuyDto;
import store.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CReceipt implements Receipt {
    private final List<BuyDto> products;
    private boolean membership;

    public CReceipt() {
        this.products = new ArrayList<>();
        this.membership = false;
    }

    @Override
    public void addProduct(BuyDto buyDto) {
        products.add(buyDto);
    }

    @Override
    public void applyMembership(boolean b) {
        this.membership = b;
    }

    /**
     * 영수증의 구매한 제품 문자열 반환
     * <pre>
     * 콜라		3 	3,000
     * 에너지바 		5 	10,000
     * </pre>
     *
     * @return 영수증의 구매한 제품 부분
     */
    @Override
    public String getProducts() {
        return products.stream().map(this::modifyProduct).collect(Collectors.joining(StringUtil.LINE_BREAK));
    }

    private String modifyProduct(BuyDto dto) {
        return String.format(StringUtil.PRODUCT_FORMAT,
                dto.getName(), dto.getAllCount(),
                StringUtil.INT_FORMAT.format(dto.getAllPrice()));
    }

    /**
     * 행사로 무료로 얻은 품목 문자열 반환
     * <pre>
     * 콜라		1
     * </pre>
     *
     * @return 영수증의 행사로 무료로 얻은 품목
     */
    @Override
    public String getPromotions() {
        return products.stream().filter(buyDto -> buyDto.getFreeCount() > 0).
                map(this::modifyProMotion).
                collect(Collectors.joining(StringUtil.LINE_BREAK));
    }

    private String modifyProMotion(BuyDto dto) {
        return String.format(StringUtil.PROMOTION_FORMAT,
                dto.getName(), dto.getFreeCount());
    }

    /**
     * 금액 정보 반환
     * <pre>
     * 총구매액		8	13,000
     * 행사할인			-1,000
     * 멤버십할인			-3,000
     * 내실돈			 9,000
     * </pre>
     *
     * @return 출력 형식에 맞춰 반환
     */
    @Override
    public String getTotal() {
        int allCount = getAllCount();
        int allPrice = getAllPrice();
        int promotionDiscount = getPromotionDiscount();
        int membershipDiscount = getMembershipDiscount();
        int totalPrice = allPrice - promotionDiscount - membershipDiscount;

        return String.format(StringUtil.TOTAL_FORMAT,
                allCount, StringUtil.INT_FORMAT.format(allPrice),
                StringUtil.INT_FORMAT.format(promotionDiscount),
                StringUtil.INT_FORMAT.format(membershipDiscount),
                StringUtil.INT_FORMAT.format(totalPrice));
    }

    private int getAllCount() {
        return products.stream().mapToInt(BuyDto::getAllCount).sum();
    }

    private int getAllPrice() {
        return products.stream().mapToInt(BuyDto::getAllPrice).sum();
    }

    private int getPromotionDiscount() {
        return products.stream().filter(buyDto -> buyDto.getFreeCount() > 0).
                mapToInt(BuyDto::getDiscount).sum();
    }

    private int getMembershipDiscount() {
        if (membership) {
            return products.stream().mapToInt(BuyDto::getNoPromotionPrice).sum() / 10 * 3;
        }
        return 0;
    }
    
    
}
