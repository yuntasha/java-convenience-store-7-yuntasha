package store.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderHelperTest {
    @Test
    @DisplayName("product 파일 읽어오기")
    void product_file_read() {
        // given
        FileReaderHelper fileReaderHelper = FileReaderHelper.getInstance();

        // when
        List<String> results = fileReaderHelper.readProductFile();

        // then
        assertEquals(16, results.size());
    }

    @Test
    @DisplayName("promotions 파일 읽어오기")
    void promotions_file_read() {
        // given
        FileReaderHelper fileReaderHelper = FileReaderHelper.getInstance();

        // when
        List<String> results = fileReaderHelper.readPromotionFile();

        // then
        assertEquals(3, results.size());
    }
}