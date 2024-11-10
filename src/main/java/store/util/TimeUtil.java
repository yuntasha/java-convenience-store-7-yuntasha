package store.util;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class TimeUtil {
    public static LocalDate getNowDate() {
        return DateTimes.now().toLocalDate();
    }
}
