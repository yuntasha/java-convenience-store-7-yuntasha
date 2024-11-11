package store.dto;

public class BuyInputDto {
    String name;
    int count;

    public BuyInputDto(String[] input) {
        this.name = input[0];
        this.count = Integer.parseInt(input[1]);
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public void removeCount(int n) {
        count -= n;
    }
}
