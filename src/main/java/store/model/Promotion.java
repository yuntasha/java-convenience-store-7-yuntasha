package store.model;

import java.time.LocalDate;

public interface Promotion {
    /**
     * 프로모션 이름 반환
     *
     * @return 프로모션 이름
     */
    String getName();

    /**
     * 기간 내인지 검사
     * start <= now <= end
     *
     * @param now 현재 날짜
     * @return 기간안이면 true
     */
    boolean isPeriod(LocalDate now);

    /**
     * 프로모션 추가 지급 여부
     *
     * @param buyCount 구매 개수
     * @return 추가 지급 가능 : true
     */
    boolean giveMore(int buyCount, LocalDate now);

    /**
     * 프로모션 한 세트 (N+1)
     *
     * @return buy+get
     */
    int getBuyPlusGet();
}
