package store.dto;

import store.model.BuyState;

import java.util.Objects;

public class BuyStateDto {
    private final BuyState buyState;
    private final int count;

    public BuyStateDto(BuyState buyState, int count) {
        this.buyState = buyState;
        this.count = count;
    }

    public BuyStateDto(BuyState buyState) {
        this.buyState = buyState;
        this.count = 0;
    }

    public BuyState getBuyState() {
        return buyState;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyStateDto buyStateDto = (BuyStateDto) o;
        return count == buyStateDto.count && buyState == buyStateDto.buyState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyState, count);
    }
}
