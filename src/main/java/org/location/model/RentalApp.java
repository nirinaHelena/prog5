package org.location.model;

import org.location.service.ItemServiceInterface;
import org.location.service.RentalService;
import org.location.service.ItemService;

import java.util.Scanner;

public class RentalApp {
    private final Scanner scanner = new Scanner(System.in);
    private final ItemServiceInterface itemService;
    private final RentalService rentalService;

    public RentalApp() {
        this.itemService = new ItemService();
        this.rentalService = new RentalService(itemService);
        ((ItemService) this.itemService).setRentalService(rentalService);
    }

    public void run() {
        itemService.loadSampleItems();

        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> itemService.listItems();
                case "2" -> rentalService.rentItem(scanner);
                case "3" -> rentalService.listActiveRentals();
                case "4" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nRental CLI:");
        System.out.println("1. See all items to be rented");
        System.out.println("2. Rent an item");
        System.out.println("3. See all active rentals");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }
}

