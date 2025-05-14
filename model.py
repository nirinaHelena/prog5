from dataclasses import dataclass
from datetime import date, timedelta
from enum import Enum


class Status(Enum):
    AVAILABLE = "AVAILABLE"
    RESERVED = "RESERVED"
    DISABLED = "DISABLED"


@dataclass
class ItemForRent:
    id: str
    name: str
    description: str
    price_per_day: float
    status: Status


class Rent:
    def __init__(self, start_date: date, days: int, item_for_rent: ItemForRent):
        if days < 1:
            raise ValueError("days must be at least one")
        self.start_date = start_date
        self.days = days
        self.end_date = start_date + timedelta(days=days)
        self.item_for_rent = item_for_rent
        self.price = item_for_rent.price_per_day * days

    def __str__(self):
        return f"Rent from {self.start_date} to {self.end_date} ({self.days} days) - ${self.price:.2f} [{self.item_for_rent.name}]"
