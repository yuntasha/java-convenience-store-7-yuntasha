package store.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static final String LINE_BREAK = "\n";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String PRODUCT_FILE = "products.md";
    public static final String PROMOTION_FILE = "promotions.md";

    private static final String FILE_DELIMITER = ",";
    private static final String FILE_ERROR_FORMAT =
            "%s을 읽어오던 중 문제가 생겼습니다.";

    public static String makeFileErrorMessage(String s) {
        return String.format(FILE_ERROR_FORMAT, s);
    }

    public static List<String> splitFileString(String s) {
        return Arrays.stream(s.split(FILE_DELIMITER)).toList();
    }
}
