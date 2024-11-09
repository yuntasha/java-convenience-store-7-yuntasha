package store.model;

import java.time.LocalDate;

public class Promotion {
    final String name;
    final int buy;
    final int get;
    final LocalDate startDate;
    final LocalDate endDate;

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
}
