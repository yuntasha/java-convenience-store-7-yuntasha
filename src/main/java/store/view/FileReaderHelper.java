package store.view;

import store.exception.FileReadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReaderHelper {

    private static FileReaderHelper fileReaderHelper;
    private static final String PRODUCT_FILE = "products.md";
    private static final String PROMOTION_FILE = "promotions.md";

    public static FileReaderHelper getInstance() {
        if (fileReaderHelper == null) {
            fileReaderHelper = new FileReaderHelper();
        }
        return fileReaderHelper;
    }

    public List<String> readProductFile() {
        List<String> products = new ArrayList<>();

        readFile(products, PRODUCT_FILE);

        return products;
    }

    public List<String> readPromotionFile() {
        List<String> promotions = new ArrayList<>();

        readFile(promotions, PROMOTION_FILE);

        return promotions;
    }

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
