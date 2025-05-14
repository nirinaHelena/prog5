const ItemForRent = require("./ItemForRent");
const Status = require("./Status");

class ItemService {
  constructor() {
    this.items = [];
  }

  setRentalService(rentalService) {
    this.rentalService = rentalService;
  }

  loadSampleItems() {
    this.items.push(new ItemForRent("1", "Camera", "HD camera", 25.0));
    this.items.push(new ItemForRent("2", "Bike", "Mountain bike", 15.0));
    this.items.push(new ItemForRent("3", "Drone", "Quadcopter", 50.0));
  }

  listItems() {
    console.log("\nAvailable items:");
    const now = new Date();
    const rents = this.rentalService.listActiveRentals().filter(r =>
      r.startDate >= now
    );
    for (const item of this.items) {
      for (const rent of rents) {
        if (
          rent.itemForRent.id === item.id &&
          this.rentalService.isBetweenRentDays(now, now, rent)
        ) {
          item.status = Status.DISABLED;
        }
      }
      console.log(`${item.id} - ${item.name} (${item.description}) - $${item.pricePerDay.toFixed(2)}/day - ${item.status}`);
    }
  }

  findById(id) {
    return this.items.find(item => item.id === id);
  }
}

module.exports = ItemService;
