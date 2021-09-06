package swcontest.dwu.blooming.diary;

public class CalendarItem {
    private String mYear, mMonth, mDay="";
    private boolean mIsTake;
    private int mYoil=0;

    CalendarItem(String y, String m, boolean isTake)
    {
        mYear = y;
        mMonth = m;
        mIsTake = isTake;
    }

    CalendarItem(String y, String m, String d, int yoil, boolean isTake)
    {
        mYear = y;
        mMonth = m;
        mDay = d;
        mYoil = yoil;
        mIsTake = isTake;
    }

    public String year()
    {
        return mYear;
    }

    public String month()
    {
        return mMonth;
    }

    public String day()
    {
        return mDay;
    }

    public Boolean isTake()
    {
        return mIsTake;
    }

    public int yoil()
    {
        return mYoil;
    }

}
