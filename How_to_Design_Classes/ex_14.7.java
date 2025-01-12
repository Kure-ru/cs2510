/* Exercise 14.7
A software house that is working with a grocery chain receives this problem statement:
. . . Develop a program that keeps track of the items in the
grocery store. For now, assume that the store deals only with
ice cream, coffee, and juice. Each of the items is specified by
its brand name (String), weight (grams) and price (cents). Each
coffee is also labeled as either regular or decaffeinated. Juice
items come in different flavors, and can be packaged as frozen,
fresh, bottled, or canned. Each package of ice cream specifies its
flavor. . . .
Design the following methods:
1. unitPrice, which computes the unit price (cents per gram) of a grocery
item;
2. lowerUnitPrice, which determines whether the unit price of a grocery
item is lower than some given amount;
3. cheaperThan, which determines whether a grocery item’s unit price is
less than some other (presumably) comparable item’s unit price.
 */


interface StoreItem {
    double unitPrice();
    boolean lowerUnitPrice(double amount);
    boolean cheaperThan(StoreItem item);
}

class IceCream implements StoreItem{
    String brand;
    double weight;
    int price;
    String flavor;

    IceCream(String brand, double weight, int price, String flavor){
        this.brand = brand;
        this.weight = weight;
        this.price = price;
        this.flavor = flavor;
    }

    public double unitPrice(){
        return this.price / this.weight;
    }

    public boolean lowerUnitPrice(double amount) {
        return this.unitPrice() < amount;
    }

    public boolean cheaperThan(StoreItem item) {
        return this.unitPrice() < item.unitPrice();
    }
}

class Coffee implements StoreItem{
    String brand;
    double weight;
    int price;
    boolean isDecaffeinated;

    Coffee(String brand, double weight, int price, boolean isDecaffeinated){
        this.brand = brand;
        this.weight = weight;
        this.price = price;
        this.isDecaffeinated = isDecaffeinated;
    }

    public double unitPrice(){
        return this.price / this.weight;
    }

    public boolean lowerUnitPrice(double amount) {
        return this.unitPrice() < amount;
    }

    public boolean cheaperThan(StoreItem item) {
        return this.unitPrice() < item.unitPrice();
    }
}

class Juice implements  StoreItem{
    String brand;
    double weight;
    int price;
    String flavor;
    String packaging;

    Juice(String brand, double weight, int price, String flavor, String packaging){
        this.brand = brand;
        this.weight = weight;
        this.price = price;
        this.flavor = flavor;
        this.packaging = packaging;
    }

    public double unitPrice(){
        return this.price / this.weight;
    }

    public boolean lowerUnitPrice(double amount) {
        return this.unitPrice() < amount;
    }

    public boolean cheaperThan(StoreItem item) {
        return this.unitPrice() < item.unitPrice();
    }
}
