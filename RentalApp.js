const readline = require("readline");
const ItemService = require("./ItemService");
const RentalService = require("./RentalService");

class RentalApp {
  constructor() {
    this.rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout
    });
    this.itemService = new ItemService();
    this.rentalService = new RentalService(this.itemService);
    this.itemService.setRentalService(this.rentalService);
  }

  run() {
    this.itemService.loadSampleItems();
    this.showMenu();
  }

  showMenu() {
    console.log("\nRental CLI:");
    console.log("1. See all items to be rented");
    console.log("2. Rent an item");
    console.log("3. See all active rentals");
    console.log("4. Exit");
    this.rl.question("Choose an option: ", choice => {
      switch (choice) {
        case "1":
          this.itemService.listItems();
          return this.showMenu();
        case "2":
          this.rentalService.rentItem(this.rl, () => this.showMenu());
          break;
        case "3":
          this.rentalService.listActiveRentals();
          return this.showMenu();
        case "4":
          console.log("Bye!");
          this.rl.close();
          break;
        default:
          console.log("Invalid option.");
          return this.showMenu();
      }
    });
  }
}

module.exports = RentalApp;
