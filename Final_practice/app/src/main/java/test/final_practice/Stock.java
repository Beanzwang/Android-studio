package test.final_practice;

/**
 * Created by wangbeanz on 11/06/2017.
 */

public class Stock {

    private String title;
    private double price;
    private long timestamp;

    public Stock(String title, double price, long timestamp) {
        this.title = title;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
