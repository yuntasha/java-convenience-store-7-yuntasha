package store.view;

import store.exception.FileReadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReaderHelper {

    private static FileReaderHelper fileReaderHelper;
    /**
     * 재고 데이터 파일명
     */
    private static final String PRODUCT_FILE = "products.md";
    /**
     * 프로모션 데이터 파일명
     */
    private static final String PROMOTION_FILE = "promotions.md";

    public static FileReaderHelper getInstance() {
        if (fileReaderHelper == null) {
            fileReaderHelper = new FileReaderHelper();
        }
        return fileReaderHelper;
    }

    /**
     * 제품 데이터 한줄씩 분리해서 반환
     *
     * @return 제품 데이터
     */
    public List<String> readProductFile() {
        List<String> products = new ArrayList<>();

        readFile(products, PRODUCT_FILE);

        return products;
    }

    /**
     * 프로모션 데이터 한줄씩 분리해서 반환
     *
     * @return 프로모션 데이터
     */
    public List<String> readPromotionFile() {
        List<String> promotions = new ArrayList<>();

        readFile(promotions, PROMOTION_FILE);

        return promotions;
    }

    /**
     * 파일 읽기
     *
     * @param lines    파일을 읽고 저장할 리스트
     * @param fileName 읽을 파일명
     */
    private void readFile(List<String> lines, String fileName) {
        try {
            InputStream inputStream = FileReaderHelper.class.getClassLoader().getResource(fileName).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines()
                    .forEach(lines::add);
            lines.remove(0);
        } catch (IOException | NullPointerException ne) {
            throw new FileReadException(fileName);
        }
    }
}
