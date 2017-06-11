package test.final_practice;

/**
 * Created by wangbeanz on 11/06/2017.
 */

public class Stock {

    private String title;
    private int price;
    private int timestamp;

    public Stock(String title, int price, int timestamp) {
        this.title = title;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getTimestamp() {
        return timestamp;
    }

}
