const Status = require("./Status");

class ItemForRent {
  constructor(id, name, description, pricePerDay, status = Status.AVAILABLE) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.pricePerDay = pricePerDay;
    this.status = status;
  }
}

module.exports = ItemForRent;
