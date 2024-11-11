package store.model;

import store.dto.BuyDto;
import store.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CReceipt implements Receipt {
    /**
     * 제품 구매 정보
     */
    private final List<BuyDto> products;
    /**
     * 멤버십 적용 여부
     */
    private boolean membership;
    /**
     * 영수증 제품 출력 형식
     */
    public static final String PRODUCT_FORMAT = "%s\t%d\t%s";
    /**
     * 영수증 프로모션 적용 제품 출력 형식
     */
    private static final String PROMOTION_FORMAT = "%s\t\t%d";
    /**
     * 영수증 금액 부분 출력 양식
     */
    private static final String TOTAL_FORMAT = "총구매액\t\t%d\t%s\n" +
            "행사할인\t\t\t-%s\n" +
            "멤버십할인\t\t\t-%s\n" +
            "내실돈\t\t\t %s";

    /**
     * 영수증 생성자
     * <pre>
     *     멤버십 기본값 미적용
     * </pre>
     */
    public CReceipt() {
        this.products = new ArrayList<>();
        this.membership = false;
    }

    /**
     * 구매할 제품 추가
     *
     * @param buyDto 구매 정보
     */
    @Override
    public void addProduct(BuyDto buyDto) {
        products.add(buyDto);
    }

    /**
     * 멤버십 적용
     *
     * @param b 멤버십 적용 여부
     */
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

    /**
     * 제품 구매 정보를 출력 형식에 맞춤
     * <pre>
     *     콜라		3 	3,000
     * </pre>
     *
     * @param dto 제품 구매 정보
     * @return 출력 형식에 맞는 제품 구매 정보
     */
    private String modifyProduct(BuyDto dto) {
        return String.format(PRODUCT_FORMAT,
                dto.getName(), dto.getAllCount(),
                StringUtil.INT_FORMAT.format(dto.getAllPrice()));
    }

    /**
     * 행사로 무료로 얻은 품목 문자열 반환
     * <pre>
     *  콜라		1
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
        return String.format(PROMOTION_FORMAT,
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

        return String.format(TOTAL_FORMAT,
                allCount, StringUtil.INT_FORMAT.format(allPrice),
                StringUtil.INT_FORMAT.format(promotionDiscount),
                StringUtil.INT_FORMAT.format(membershipDiscount),
                StringUtil.INT_FORMAT.format(totalPrice));
    }

    /**
     * 구매한 모든 제품 총 개수 반환
     *
     * @return 총 개수 반환
     */
    private int getAllCount() {
        return products.stream().mapToInt(BuyDto::getAllCount).sum();
    }

    /**
     * 구매한 모든 제품 총 개수 반환
     *
     * @return 총 금액 반환
     */
    private int getAllPrice() {
        return products.stream().mapToInt(BuyDto::getAllPrice).sum();
    }

    /**
     * 프로모션으로 할인될 금액 반환
     *
     * @return 할인될 금액 반환
     */
    private int getPromotionDiscount() {
        return products.stream().filter(buyDto -> buyDto.getFreeCount() > 0).
                mapToInt(BuyDto::getDiscount).sum();
    }

    /**
     * 멤버십 할인 금액 반환
     * <pre>
     *     멤버십 미적용의 경우 0 반환
     * </pre>
     *
     * @return 멤버십 할인 금액
     */
    private int getMembershipDiscount() {
        if (membership) {
            return products.stream().mapToInt(BuyDto::getNoPromotionPrice).sum() / 10 * 3;
        }
        return 0;
    }
}
