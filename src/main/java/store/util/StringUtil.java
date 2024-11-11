package store.util;

import store.dto.ProductDetailDto;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringUtil {
    // 기본 문자열 정의
    public static final String LINE_BREAK = "\n";
    public static final String HIGH_BAR = "-";
    public static final String COMMA = ",";
    public static final String EMPTY = "";

    /**
     * 출력 형식을 맞추기 위해 적용
     */
    private static final DecimalFormat INT_FORMAT = new DecimalFormat("###,###");
    /**
     * 파일 에러 메세지 형식
     */
    private static final String FILE_ERROR_FORMAT =
            "[Error] %s을 읽어오던 중 문제가 생겼습니다.";
    /**
     * 재고를 보여줄 때 기본 제품이 출력될 형식
     */
    private static final String PRODUCT_DETAIL_NO_PROMOTION_FORMAT = "- %s %s원 %s";
    /**
     * 재고를 보여줄 때 프로모션 제품이 출력될 형식
     */
    private static final String PRODUCT_DETAIL_PROMOTION_FORMAT = "- %s %s원 %s %s";
    private static final String NO_QUANTITY = "재고 없음";
    private static final String COUNT = "개";

    /**
     * 파일 에러 발생시 콘솔에 내보낼 오류
     * <pre>
     *     [Error] {파일 이름}을 읽어오던 중 문제가 생겼습니다.
     * </pre>
     *
     * @param s 파일 이름
     * @return 파일 에러 메세지
     */
    public static String makeFileErrorMessage(String s) {
        return String.format(FILE_ERROR_FORMAT, s);
    }

    /**
     * COMMA(,)를 기준으로 분리
     *
     * @param s 입력 문자열
     * @return 분리된 문자열
     */
    public static List<String> splitFileString(String s) {
        return Arrays.stream(s.split(COMMA)).toList();
    }

    /**
     * 재고 정보를 형식에 맞게 변환
     * <pre>
     *     프로모션 적용된 경우
     *     - 감자칩 1,500원 5개 반짝할인
     *     - 감자칩 1,500원 5개
     *
     *     프로모션 미적용된 경우
     *     - 물 500원 10개
     * </pre>
     *
     * @param dto 재고 정보
     * @return 출력 형식에 맞춘 재고 정보
     */
    public static String dtoToString(ProductDetailDto dto) {
        StringBuilder result = new StringBuilder();

        if (!Objects.isNull(dto.getPromotionName())) {
            result.append(String.format(PRODUCT_DETAIL_PROMOTION_FORMAT,
                    dto.getName(), intToPriceFormat(dto.getPrice()),
                    quantityToString(dto.getPQuantity()), dto.getPromotionName()));
            result.append(LINE_BREAK);
        }
        result.append(String.format(PRODUCT_DETAIL_NO_PROMOTION_FORMAT,
                dto.getName(), intToPriceFormat(dto.getPrice()),
                quantityToString(dto.getQuantity())));
        return result.toString();
    }

    /**
     * 1,000,000과 같이 변환
     *
     * @param n 숫자
     * @return 변환된 문자열
     */
    public static String intToPriceFormat(int n) {
        return INT_FORMAT.format(n);
    }

    /**
     * 재고를 출력 형식에 맞게 변환
     * <pre>
     *     재고가 있는 경우 -> n개
     *     재고가 없는 경우 -> 재고 없음
     * </pre>
     *
     * @param n 재고
     * @return 형식에 맞는 재고
     */
    private static String quantityToString(int n) {
        if (n == 0) {
            return NO_QUANTITY;
        }
        return n + COUNT;
    }
}
