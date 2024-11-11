package store.dto;

public class BuyInputDto {
    /**
     * 구매할 제품 이름
     */
    String name;
    /**
     * 구매할 제품 개수
     */
    int count;

    /**
     * 구매 정보 생성
     * <pre>
     *     ["사이다", "2"]
     * </pre>
     * @param input 구매 정보
     */
    public BuyInputDto(String[] input) {
        this.name = input[0];
        this.count = Integer.parseInt(input[1]);
    }

    /**
     * 제품 이름 반환
     * @return 제품 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 구매할 제품 개수 반환
     * @return 제품 개수
     */
    public int getCount() {
        return count;
    }

    /**
     * 제품을 1개 추가 구매
     * <pre>
     *     프로모션 할인을 받을 수 있는 경우 사용하기 위해 만들어진 메서드
     * </pre>
     */
    public void addCount() {
        count++;
    }

    /**
     * 구매할 제품 개수를 n개 제거합니다
     * <pre>
     *     프로모션 미적용을 제품을 빼기 위해 만들어진 메서드
     * </pre>
     * @param n 제거할 제품 수
     */
    public void removeCount(int n) {
        count -= n;
    }
}
