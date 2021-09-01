package swcontest.dwu.blooming.dto;

import java.io.Serializable;

public class LocationDto implements Serializable {

    private long id;
    private double latitude;
    private double longitude;
    private String date;
    private String time;

    public LocationDto(long id, String date, String time, double latitude, double longitude) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDto(String date) {
        this.date = date;
    }

    public LocationDto(String date, String time, double latitude, double longitude) {
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}