package store.util;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class TimeUtil {
    /**
     * 현재 시간 불러옴
     *
     * @return 현재 시간
     */
    public static LocalDate getNowDate() {
        return DateTimes.now().toLocalDate();
    }
}
