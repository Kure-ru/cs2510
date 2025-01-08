package labs.lab1;

interface IMenuItem {

}

class Sandwich implements IMenuItem{
    String name;
    int price;
    String bread;
    String filling1;
    String filling2;

    Sandwich(String name, int price, String bread, String filling1, String filling2){
        this.name = name;
        this.price = price;
        this.bread = bread;
        this.filling1 = filling1;
        this.filling2 = filling2;
    }
}

class Soup implements IMenuItem{
    String name;
    int price;
    boolean vegetarian;

    Soup(String name, int price, boolean vegetarian){
        this.name = name;
        this.price = price;
        this.vegetarian = vegetarian;
    }
}

class Salad implements IMenuItem{
    String name;
    int price;
    boolean vegetarian;
    String dressing;

    Salad(String name, int price, boolean vegetarian, String dressing){
        this.name = name;
        this.price = price;
        this.vegetarian = vegetarian;
        this.dressing = dressing;
    }
}

class ExamplesMenuItems {
    ExamplesMenuItems(){}

    Sandwich s1 = new Sandwich("BLT", 5, "white", "bacon", "lettuce");
    Sandwich s2 = new Sandwich("Veggie", 5, "white", "lettuce", "tomato");
    Soup s3 = new Soup("Tomato", 3, true);
    Soup s4 = new Soup("Chicken Noodle", 3, false);
    Salad s5 = new Salad("Caesar", 4, false, "Caesar");
    Salad s6 = new Salad("Greek", 4, true, "Greek");
}

