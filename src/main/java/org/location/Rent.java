package org.location;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Builder
public class Rent {
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;
    private double price;
    private ItemForRent itemForRent;

    public Rent(LocalDate startDate, int days, ItemForRent itemForRent) {
        if (days < 0){
            throw new IllegalArgumentException("days must be greater than zero");
        }
        this.startDate = startDate;
        this.days = days;
        this.endDate = startDate.plusDays(days);
        this.itemForRent = itemForRent;
        this.price = itemForRent.getPricePerDay() * this.days;
    }

    public Rent setStartDate(LocalDate startDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("startDate must be after now");
        }
        this.startDate = startDate;
        this.endDate = startDate.plusDays(days);
        return this;
    }

    public Rent setDays(int days) {
        if (days < 0){
            throw new IllegalArgumentException("days must be greater than zero");
        }
        this.days = days;
        this.endDate = startDate.plusDays(days);
        this.price = itemForRent.getPricePerDay() * this.days;
        return this;
    }

    public Rent setItemForRent(ItemForRent itemForRent) {
        this.itemForRent = itemForRent;
        this.price = itemForRent.getPricePerDay() * this.days;
        return this;
    }
}
