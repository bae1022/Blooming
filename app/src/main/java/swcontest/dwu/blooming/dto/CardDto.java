package swcontest.dwu.blooming.dto;

import java.io.Serializable;

public class CardDto implements Serializable {

    private long _id;
    private String title;
    private String date;
    private String price;

    public CardDto(){

    }

    public CardDto(long _id, String title, String date, String price){
        this._id = _id;
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public CardDto(String title, String date, String price){
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}