from model import Rent, Status
from datetime import date


class RentalService:
    def __init__(self, item_service):
        self.rentals = []
        self.item_service = item_service

    def rent_item(self, scanner):
        item_id = input("Enter item ID to rent: ")
        item = self.item_service.find_by_id(item_id)

        if item is None:
            print("Item not found.")
            return

        if item.status == Status.DISABLED:
            print("Sorry, item is disabled")
            return

        start_date = self.prompt_date("Enter start date (YYYY-MM-DD): ")
        end_date = self.prompt_date("Enter end date (YYYY-MM-DD): ")

        if end_date <= start_date:
            print("Rental must be at least 1 day long.")
            return

        if date.today() > start_date:
            print("Rental must be in the future")
            return

        for rent in self.rentals:
            if rent.item_for_rent.id == item_id and self.is_between_rent_days(start_date, end_date, rent):
                print(f"Item is already rented from {rent.start_date} to {rent.end_date}")
                return

        duration = (end_date - start_date).days
        rent = Rent(start_date, duration, item)
        item.status = Status.RESERVED
        self.rentals.append(rent)
        print("Rental successful:", rent)

    def is_between_rent_days(self, start_date, end_date, rent: Rent):
        rent_start = rent.start_date
        rent_end = rent.end_date
        return rent_start <= end_date and rent_end >= start_date

    def list_active_rentals(self):
        today = date.today()
        print("\nActive Rentals:")
        active = [r for r in self.rentals if r.end_date > today]
        for r in active:
            print(f"{r.item_for_rent.name} rented from {r.start_date} to {r.end_date} (${r.price:.2f})")
        return active

    def prompt_date(self, prompt: str):
        while True:
            try:
                return date.fromisoformat(input(prompt))
            except ValueError:
                print("Invalid date format. Use YYYY-MM-DD.")
