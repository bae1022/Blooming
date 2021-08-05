package swcontest.dwu.blooming.userSetting;

import java.util.Date;

public class UserData {
    //추후 이름, 생년월일, 주소, 추적주기, 기상취침시간, 보호자번호로 변경
//    private String name;
//    private Date birth;
//    private String address;
//    private int period;
//    private Date wakeTime;
//    private Date bedTime;
//    private String phone;
//    int check;
//
//    public UserData(int check){
//        this.check = check;
//    }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public Date getBirth() { return birth; }
//    public void setBirth(Date birth) { this.birth = birth; }
//
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//
//    public int getPeriod() { return period; }
//    public void setPeriod(int period) { this.period = period; }
//
//    public Date getWakeTime() { return wakeTime; }
//    public void setWakeTime(Date wakeTime) { this.wakeTime = wakeTime; }
//
//    public Date getBedTime() { return bedTime; }
//    public void setBedTime(Date bedTime) { this.bedTime = bedTime; }
//
//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//
//    public int getCheck() { return check; }
//    public void setCheck(int check) { this.check = check; }

    int color;
    String title;

    public UserData(int color, String title){
        this.color = color;
        this.title = title;
    }

    public int getColor(){ return color; }
    public void setColor(int color){ this.color = color; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }
}
