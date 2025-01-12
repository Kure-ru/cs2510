/* Exercise 3.2
Translate the data definition in figure 11 into classes. Also obtain examples of weather information and translate them into instances of
the matching class.
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
}

class WeatherRecord {
    Date d;
    TemperatureRange today;
    TemperatureRange normal;
    TemperatureRange record;

    WeatherRecord(Date d, TemperatureRange today, TemperatureRange normal, TemperatureRange record){
        this.d = d;
        this.today = today;
        this.normal = normal;
        this.record = record;
    }
}