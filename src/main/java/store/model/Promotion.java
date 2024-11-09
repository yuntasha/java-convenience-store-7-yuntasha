package store.model;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    /**
     * 기간 내인지 검사
     * start <= now <= end
     *
     * @param now 현재 날짜
     * @return 기간안이면 true
     */
    public boolean isPeriod(LocalDate now) {
        return now.isAfter(startDate) && now.isBefore(endDate) || now.isEqual(startDate) || now.isEqual(endDate);
    }

    /**
     * 프로모션 추가 지급 여부
     *
     * @param buyCount 구매 개수
     * @return 추가 지급 가능 : true
     */
    public boolean giveMore(int buyCount, LocalDate now) {
        if (isPeriod(now)) {
            return buyCount % (buy + get) == buy;
        }

        return false;
    }

    /**
     * 프로모션 한 세트 (N+1)
     *
     * @return buy+get
     */
    public int getBuyPlusGet() {
        return buy + get;
    }
}
