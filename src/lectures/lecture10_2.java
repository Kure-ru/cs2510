package lectures;

import tester.Tester;

interface IDate {

}

class Date implements IDate {
    int year;
    int month;
    int day;

    Date(int year, int month, int day){
        this.year = (new Utils()).checkRange(year, 1500, 2100, "Invalid year: " + year);
        this.month = (new Utils()).checkRange(month, 1, 12, "Invalid month: " + month);
        this.day = (new Utils()).checkRange(day, 1, 31, "Invalid day: " + day);
    }
}

class Utils {
    Utils(){}

    int checkRange(int value, int low, int high, String message){
        if (value >= low && value <= high){
            return value;
        } else {
            throw new IllegalArgumentException(message);
        }
    }
}

class ExamplesDates {
    ExamplesDates(){}

    // Good dates
    Date d20100228 = new Date(2010, 2, 28);   // Feb 28, 2010
    Date d20091012 = new Date(2009, 10, 12);  // Oct 12, 2009


    boolean testCheckConstructorException(Tester t){
        return t.checkConstructorException(new IllegalArgumentException("Invalid year: -30"), "Date", -30, 33, 23);
    }
}