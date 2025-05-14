class Rent {
    constructor(startDate, days, itemForRent) {
      if (days < 1) throw new Error("days must be at least one");
      this.startDate = new Date(startDate);
      this.days = days;
      this.endDate = new Date(this.startDate);
      this.endDate.setDate(this.startDate.getDate() + days);
      this.itemForRent = itemForRent;
      this.price = itemForRent.pricePerDay * days;
    }
  
    toString() {
      return `Rent from ${this.startDate.toISOString().split('T')[0]} to ${this.endDate.toISOString().split('T')[0]} (${this.days} days) - $${this.price.toFixed(2)} [${this.itemForRent.name}]`;
    }
  }
  
  module.exports = Rent;
  