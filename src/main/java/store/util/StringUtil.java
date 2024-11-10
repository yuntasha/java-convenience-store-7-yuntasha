package store.util;

import store.dto.BuyInputDto;
import store.dto.ProductDetailDto;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringUtil {
    public static final String LINE_BREAK = "\n";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String PRODUCT_FILE = "products.md";
    public static final String PROMOTION_FILE = "promotions.md";
    public static final String HELLO_MESSAGE = "안녕하세요. W편의점입니다.\n" +
            "현재 보유하고 있는 상품입니다.\n" +
            "\n";
    public static final String ADD_MESSAGE_FORMAT = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String REMOVE_MESSAGE_FORMAT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    public static final String MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String CONTINUE_SHOPPING_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    public static final String BUY_INPUT_FORMAT = "^\\[[가-힣]+-[1-9][0-9]*\\](,\\[[가-힣]+-[1-9][0-9]*\\])*$";
    public static final String HIGH_BAR = "-";
    public static final String COMMA = ",";
    public static final String W_CONVENIENCE = "==============W 편의점================";
    public static final String PROMOTION_TITLE = "=============증\t정===============";
    public static final String DELIMITER = "====================================";

    private static final String FILE_ERROR_FORMAT =
            "%s을 읽어오던 중 문제가 생겼습니다.";
    private static final String PRODUCT_DETAIL_NO_PROMOTION_FORMAT = "- %s %s원 %s";
    private static final String PRODUCT_DETAIL_PROMOTION_FORMAT = "- %s %s원 %s %s";
    private static final DecimalFormat INT_FORMAT = new DecimalFormat("###,###");
    private static final String NO_QUANTITY = "재고 없음";
    private static final String COUNT = "개";

    public static String makeFileErrorMessage(String s) {
        return String.format(FILE_ERROR_FORMAT, s);
    }

    public static List<String> splitFileString(String s) {
        return Arrays.stream(s.split(COMMA)).toList();
    }

    public static String dtoToString(ProductDetailDto dto) {
        StringBuilder result = new StringBuilder();

        if (!Objects.isNull(dto.getPromotionName())) {
            result.append(String.format(PRODUCT_DETAIL_PROMOTION_FORMAT,
                    dto.getName(), INT_FORMAT.format(dto.getPrice()),
                    quantityToString(dto.getPQuantity()), dto.getPromotionName()));
            result.append(LINE_BREAK);
        }
        result.append(String.format(PRODUCT_DETAIL_NO_PROMOTION_FORMAT,
                dto.getName(), INT_FORMAT.format(dto.getPrice()),
                quantityToString(dto.getQuantity())));
        return result.toString();
    }

    private static String quantityToString(int n) {
        if (n == 0) {
            return NO_QUANTITY;
        }
        return String.valueOf(n) + COUNT;
    }
}
