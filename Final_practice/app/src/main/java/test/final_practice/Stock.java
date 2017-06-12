package test.final_practice;

/**
 * Created by wangbeanz on 11/06/2017.
 */

public class Stock {

    private int id;
    private String title;
    private double price;
    private long timestamp;

    public Stock(String title, double price, long timestamp) {
        this(0, title, price, timestamp);
    }

    public Stock(int id, String title, double price, long timestamp) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }

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
