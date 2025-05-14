package org.location.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Rent {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int days;
    private final double price;
    private final ItemForRent itemForRent;

    public Rent(LocalDate startDate, int days, ItemForRent itemForRent) {
        if (days < 1) {
            throw new IllegalArgumentException("days must be at least one");
        }
        this.startDate = startDate;
        this.days = days;
        this.endDate = startDate.plusDays(days);
        this.itemForRent = itemForRent;
        this.price = itemForRent.getPricePerDay() * this.days;
    }

    @Override
    public String toString() {
        return String.format("Rent from %s to %s (%d days) - $%.2f [%s]",
                startDate, endDate, days, price, itemForRent.getName());
    }
}
