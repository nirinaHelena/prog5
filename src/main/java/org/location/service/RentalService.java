package org.location.service;

import lombok.RequiredArgsConstructor;
import org.location.model.ItemForRent;
import org.location.model.Rent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static java.time.LocalDate.now;
import static org.location.model.Status.DISABLED;
import static org.location.model.Status.RESERVED;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final List<Rent> rentals = new ArrayList<>();
    private final ItemServiceInterface itemService;

    public void rentItem(Scanner scanner) {
        System.out.print("Enter item ID to rent: ");
        String itemId = scanner.nextLine();
        Optional<ItemForRent> optionalItem = itemService.findById(itemId);

        if (optionalItem.isEmpty()) {
            System.out.println("Item not found.");
            return;
        }

        ItemForRent item = optionalItem.get();

        if (item.getStatus() == DISABLED){
            System.out.println("Sorry, item is disable");
        }

        LocalDate startDate = promptDate(scanner, "Enter start date (YYYY-MM-DD): ");
        LocalDate endDate = promptDate(scanner, "Enter end date (YYYY-MM-DD): ");

        if (!endDate.isAfter(startDate)) {
            System.out.println("Rental must be at least 1 day long.");
            return;
        }
        if (now().isAfter(startDate)){
            System.out.println("Rental must be in the future");
            return;
        }

        for (Rent r : rentals) {
            if (r.getItemForRent().getId().equals(itemId) && isBetweenRentDays(startDate, endDate, r)) {
                System.out.printf("Item is already rented from %s to %s%n",
                        r.getStartDate(), r.getEndDate());
                return;
            }
        }

        int duration = (int) (endDate.toEpochDay() - startDate.toEpochDay());
        Rent rent = new Rent(startDate, duration, item);
        item.setStatus(RESERVED);
        rentals.add(rent);
        System.out.println("Rental successful: " + rent);
    }

    public boolean isBetweenRentDays(LocalDate startDate, LocalDate endDate, Rent rent) {
        LocalDate rentStartDate = rent.getStartDate();
        LocalDate rentEndDate = rent.getEndDate();

        return (rentStartDate.isAfter(startDate) || rentStartDate.equals(startDate))
                && (rentEndDate.isBefore(endDate) || rentEndDate.equals(endDate));
    }

    public List<Rent> listActiveRentals() {
        LocalDate today = now();
        System.out.println("\nActive Rentals:");
        List<Rent> rentList = rentals.stream()
                .filter(r -> r.getEndDate().isAfter(today)).toList();
        rentList.forEach(r -> System.out.printf("%s rented from %s to %s ($%.2f)%n",
                r.getItemForRent().getName(),
                r.getStartDate(), r.getEndDate(), r.getPrice()));
        return rentList;
    }

    private LocalDate promptDate(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
}
