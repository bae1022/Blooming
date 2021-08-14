package swcontest.dwu.blooming.dto;

import java.io.Serializable;

public class DiaryDto implements Serializable {
    private long _id;
    private String date;
    private String diary;

    public DiaryDto(String s, String toString){

    }

    public DiaryDto(long _id, String date, String diary){
        this._id = _id;
        this.date = date;
        this.diary = diary;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }
}
