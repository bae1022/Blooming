package swcontest.dwu.blooming.userSetting;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable {
    //이름
    private String name;
    //생년월일
    private int month;
    private int day;
    private int year;
    //주소
    private String address;
    //위치추적 주기
    private int period;
    //기상,취침시각
    private Date wakeTime;
    private Date bedTime;
    //보호자 번호
    private String phone;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }

    public Date getWakeTime() {
        return wakeTime;
    }
    public void setWakeTime(Date wakeTime) {
        this.wakeTime = wakeTime;
    }

    public Date getBedTime() {
        return bedTime;
    }
    public void setBedTime(Date bedTime) {
        this.bedTime = bedTime;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //    int color;
//    String title;
//
//    public UserData(int color, String title){
//        this.color = color;
//        this.title = title;
//    }
//
//    public int getColor(){ return color; }
//    public void setColor(int color){ this.color = color; }
//
//    public String getTitle(){ return title; }
//    public void setTitle(String title){ this.title = title; }
}
