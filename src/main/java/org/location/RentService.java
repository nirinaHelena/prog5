package org.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RentService {
    private final Rent rent;

    public Rent reserve(LocalDate startDate, int duration, ItemForRent itemForRent) {
        Rent toReserve = new Rent(startDate, duration, itemForRent);
    }
}
