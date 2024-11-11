package store.util;

import store.dto.BuyInputDto;
import store.dto.ProductDetailDto;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringUtil {
    public static final String LINE_BREAK = "\n";
    public static final String HIGH_BAR = "-";
    public static final String COMMA = ",";
    public static final String PRODUCT_TITLE = "상품명\t\t수량\t금액";
    public static final String PRODUCT_FORMAT = "%s\t%d\t%s";
    public static final String PROMOTION_TITLE = "=============증\t정===============";
    public static final String PROMOTION_FORMAT = "%s\t\t%d";
    public static final String DELIMITER = "====================================";
    public static final String TOTAL_FORMAT = "총구매액\t\t%d\t%s\n" +
            "행사할인\t\t\t-%s\n" +
            "멤버십할인\t\t\t-%s\n" +
            "내실돈\t\t\t %s";
    public static final DecimalFormat INT_FORMAT = new DecimalFormat("###,###");
    public static final String EMPTY = "";

    private static final String FILE_ERROR_FORMAT =
            "%s을 읽어오던 중 문제가 생겼습니다.";
    private static final String PRODUCT_DETAIL_NO_PROMOTION_FORMAT = "- %s %s원 %s";
    private static final String PRODUCT_DETAIL_PROMOTION_FORMAT = "- %s %s원 %s %s";
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
