from item_service import ItemService
from rental_service import RentalService


class RentalApp:
    def __init__(self):
        self.item_service = ItemService()
        self.rental_service = RentalService(self.item_service)
        self.item_service.set_rental_service(self.rental_service)

    def run(self):
        self.item_service.load_sample_items()

        while True:
            self.print_menu()
            choice = input()
            if choice == "1":
                self.item_service.list_items()
            elif choice == "2":
                self.rental_service.rent_item(input)
            elif choice == "3":
                self.rental_service.list_active_rentals()
            elif choice == "4":
                print("Bye!")
                return
            else:
                print("Invalid option.")

    def print_menu(self):
        print("\nRental CLI:")
        print("1. See all items to be rented")
        print("2. Rent an item")
        print("3. See all active rentals")
        print("4. Exit")
        print("Choose an option: ", end="")
