package store.model;

import java.time.LocalDate;

public class CPromotion implements Promotion {
    /**
     * 프로모션 이름
     */
    private final String name;
    /**
     * N+1 행사에서 N
     */
    private final int buy;
    /**
     * N+1 행사에서 1
     * <pre>
     *     2+2와 같은 경우를 위해 만들어 두었지만 이 미션에서 1로 사용
     * </pre>
     */
    private final int get;
    /**
     * 프로모션 시작 날짜
     */
    private final LocalDate startDate;
    /**
     * 프로모션 마지막 날짜
     */
    private final LocalDate endDate;

    /**
     * 프로모션 생성
     *
     * @param name      이름
     * @param buy       N+1 행사에서 N
     * @param get       N+1 행사에서 1
     * @param startDate 프로모션 시작 날짜
     * @param endDate   프로모션 마지막 날짜
     */
    public CPromotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 프로모션 이름 반환
     *
     * @return 프로모션 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 기간 내인지 검사
     * <pre>
     * start <= now <= end
     * </pre>
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
