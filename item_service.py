from model import ItemForRent, Status
from datetime import date


class ItemServiceInterface:
    def load_sample_items(self):
        raise NotImplementedError

    def list_items(self):
        raise NotImplementedError

    def find_by_id(self, item_id: str):
        raise NotImplementedError


class ItemService(ItemServiceInterface):
    def __init__(self):
        self.items = []
        self.rental_service = None

    def set_rental_service(self, rental_service):
        self.rental_service = rental_service

    def load_sample_items(self):
        self.items.append(ItemForRent("1", "Camera", "HD camera", 25.0, Status.AVAILABLE))
        self.items.append(ItemForRent("2", "Bike", "Mountain bike", 15.0, Status.AVAILABLE))
        self.items.append(ItemForRent("3", "Drone", "Quadcopter", 50.0, Status.AVAILABLE))

    def list_items(self):
        print("\nAvailable items:")
        from datetime import date
        today = date.today()
        rents = [r for r in self.rental_service.list_active_rentals()
                 if r.start_date >= today]

        for item in self.items:
            for rent in rents:
                if rent.item_for_rent.id == item.id and self.rental_service.is_between_rent_days(today, today, rent):
                    item.status = Status.DISABLED
            print(f"{item.id} - {item.name} ({item.description}) - ${item.price_per_day:.2f}/day - {item.status.name}")

    def find_by_id(self, item_id):
        return next((i for i in self.items if i.id == item_id), None)
