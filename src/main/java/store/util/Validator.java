package store.util;

import java.time.LocalDate;

public class Validator {
    /**
     * 기간 내인지 검사
     * start <= now <= end
     *
     * @param now   현재 날짜
     * @param start 시작 날짜
     * @param end   끝난 날짜
     * @return 기간안이면 true
     */
    public static boolean isPeriod(LocalDate now, LocalDate start, LocalDate end) {
        return now.isAfter(start) && now.isBefore(end) || now.isEqual(start) || now.isEqual(end);
    }
}
