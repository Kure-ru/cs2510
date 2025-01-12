/* Exercise 11.2
Exercise 3.2 provides the data definition for a weather recording program. Design the following methods for the WeatherRecord class:
1. withinRange, which determines whether today’s high and low were
within the normal range;
2. rainyDay, which determines whether the precipitation is higher than
some given value;
3. recordDay, which determines whether the temperature broke either
the high or the low record.
 */

class Date {
    int day;
    int month;
    int year;

    Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
}

class TemperatureRange {
    int high;
    int low;

    TemperatureRange(int high, int low){
        this.high = high;
        this.low = low;
    }

    int difference(){
        return this.high - this.low;
    }

    boolean withinRange(TemperatureRange that){
        return this.low <= that.low && this.high >= that.high;
    }
}

class WeatherRecord {
    Date d;
    TemperatureRange today;
    TemperatureRange normal;
    TemperatureRange record;
    double precipitation;

    WeatherRecord(Date d, TemperatureRange today, TemperatureRange normal, TemperatureRange record, double precipitation){
        this.d = d;
        this.today = today;
        this.normal = normal;
        this.record = record;
        this.precipitation = precipitation;
    }

    int differential(){
        return this.today.difference();
    }

    boolean withinRange(){
        return this.normal.withinRange(this.today);
    }

    boolean rainyDay(double baseValue){
        return this.precipitation > baseValue;
    }

    boolean recordDay(){
        return this.today.high > this.record.high ||  this.today.low < this.record.low;
    }
}
