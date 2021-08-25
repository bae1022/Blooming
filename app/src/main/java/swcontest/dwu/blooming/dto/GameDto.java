package swcontest.dwu.blooming.dto;

public class GameDto {
    public String display;
    public String tag;
    public int check = 0;

    public GameDto(String display, String tag) {
        this.display = display;
        this.tag = tag;
    }

    public String getDisplay() {
        return display;
    }

    String getTag() {
        return tag;
    }

    public int getCheck() {
        return check;
    }

    void setDisplay(String display) {
        this.display = display;
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
