const Rent = require("./Rent");
const Status = require("./Status");

class RentalService {
  constructor(itemService) {
    this.itemService = itemService;
    this.rentals = [];
  }

  rentItem(rl, callback) {
    rl.question("Enter item ID to rent: ", itemId => {
      const item = this.itemService.findById(itemId);
      if (!item) {
        console.log("Item not found.");
        return callback();
      }
      if (item.status === Status.DISABLED) {
        console.log("Sorry, item is disabled");
        return callback();
      }

      this.promptDate(rl, "Enter start date (YYYY-MM-DD): ", startDate => {
        this.promptDate(rl, "Enter end date (YYYY-MM-DD): ", endDate => {
          const start = new Date(startDate);
          const end = new Date(endDate);
          if (end <= start) {
            console.log("Rental must be at least 1 day long.");
            return callback();
          }
          const now = new Date();
          if (start <= now) {
            console.log("Rental must be in the future");
            return callback();
          }

          for (const r of this.rentals) {
            if (
              r.itemForRent.id === itemId &&
              this.isBetweenRentDays(start, end, r)
            ) {
              console.log(`Item is already rented from ${r.startDate.toISOString().split('T')[0]} to ${r.endDate.toISOString().split('T')[0]}`);
              return callback();
            }
          }

          const duration = Math.round((end - start) / (1000 * 60 * 60 * 24));
          const rent = new Rent(startDate, duration, item);
          item.status = Status.RESERVED;
          this.rentals.push(rent);
          console.log("Rental successful: " + rent.toString());
          callback();
        });
      });
    });
  }

  isBetweenRentDays(startDate, endDate, rent) {
    return rent.startDate <= endDate && rent.endDate >= startDate;
  }

  listActiveRentals() {
    const today = new Date();
    console.log("\nActive Rentals:");
    const result = this.rentals.filter(r => r.endDate > today);
    for (const r of result) {
      console.log(`${r.itemForRent.name} rented from ${r.startDate.toISOString().split('T')[0]} to ${r.endDate.toISOString().split('T')[0]} ($${r.price.toFixed(2)})`);
    }
    return result;
  }

  promptDate(rl, prompt, callback) {
    rl.question(prompt, date => {
      if (!/^\\d{4}-\\d{2}-\\d{2}$/.test(date)) {
        console.log("Invalid date format. Use YYYY-MM-DD.");
        return this.promptDate(rl, prompt, callback);
      }
      callback(date);
    });
  }
}

module.exports = RentalService;
