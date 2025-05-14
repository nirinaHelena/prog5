package org.location.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.location.model.Rent;
import org.location.model.Status;
import org.location.model.ItemForRent;
import org.springframework.stereotype.Service;
import java.util.*;

import static java.time.LocalDate.now;
import static org.location.model.Status.DISABLED;

@Service
@RequiredArgsConstructor
public class ItemService implements ItemServiceInterface{
    private final List<ItemForRent> items = new ArrayList<>();
    @Setter
    private RentalService rentalService;


    @Override
    public void loadSampleItems() {
        items.add(new ItemForRent("1", "Camera", "HD camera", 25.0, Status.AVAILABLE));
        items.add(new ItemForRent("2", "Bike", "Mountain bike", 15.0, Status.AVAILABLE));
        items.add(new ItemForRent("3", "Drone", "Quadcopter", 50.0, Status.AVAILABLE));
    }

    @Override
    public void listItems() {
        System.out.println("\nAvailable items:");
        List<Rent> rents = rentalService.listActiveRentals()
                .stream()
                .filter(
                        r -> r.getStartDate().equals(now()) || r.getStartDate().isAfter(now()))
                .toList();
        for (ItemForRent item : items) {
            for (Rent rent : rents){
                if (rent.getItemForRent().getId().equals(item.getId())
                        && rentalService.isBetweenRentDays(now(), now(), rent)) {
                    item.setStatus(DISABLED);
                }
            }
            System.out.printf("%s - %s (%s) - $%.2f/day - %s%n",
                    item.getId(), item.getName(), item.getDescription(),
                    item.getPricePerDay(), item.getStatus());
        }
    }

    public Optional<ItemForRent> findById(String id) {
        return items.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}