package swcontest.dwu.blooming.userSetting;

import java.util.Date;

public class UserData {
    //추후 이름, 생년월일, 주소, 추적주기, 기상취침시간, 보호자번호로 변경
    private String name;
    private Date birth;
    private String address;
    private int period;
    private Date wakeTime;
    private Date bedTime;
    private String phone;

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
