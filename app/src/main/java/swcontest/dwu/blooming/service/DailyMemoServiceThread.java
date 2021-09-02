package swcontest.dwu.blooming.service;


import android.os.Handler;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import swcontest.dwu.blooming.db.UserDBHelper;

import static swcontest.dwu.blooming.MainActivity.hour_sleep;
import static swcontest.dwu.blooming.MainActivity.hour_wake;
import static swcontest.dwu.blooming.MainActivity.minute_sleep;
import static swcontest.dwu.blooming.MainActivity.minute_wake;

public class DailyMemoServiceThread extends Thread {

    Handler handler;
    boolean isRun = true;

    UserDBHelper helper;

    public DailyMemoServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized(this){
            this.isRun = false;
        }
    }

    public void run() {

//        if (getH - Integer.parseInt(hour_wake) >= 0 && (getH - Integer.parseInt(hour_wake)) % 4 == 0 && getM == Integer.parseInt(minute_wake) && getSeconds.equals("00")) {

        while (isRun) {
            //현재 시간 구함
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat simpleDateH = new SimpleDateFormat("HH");
            String getHour = simpleDateH.format(mDate);

            SimpleDateFormat simpleDateM = new SimpleDateFormat("mm");
            String getMinute = simpleDateM.format(mDate);

            SimpleDateFormat simpleDateS = new SimpleDateFormat("ss");
            String getSeconds = simpleDateS.format(mDate);

            int getH = Integer.valueOf(getHour);
            int getM = Integer.valueOf(getMinute);

//            (getH - Integer.parseInt(hour_wake)) % 3 == 0 && 여기 아래줄부터 if문 주석처리 풀기 나연아!!!!!!!!!!!!!!!!!!!!!!!
//            if (getH - Integer.parseInt(hour_wake) > 0 &&  (getH - Integer.parseInt(hour_wake)) % 3 == 0 && getM == Integer.parseInt(minute_wake) && getSeconds.equals("00")) {
//                if (Integer.parseInt(hour_sleep) > getH){
//                    handler.sendEmptyMessage(0);
//                }
//                else if (Integer.parseInt(hour_sleep) == getH){
//                    if (Integer.parseInt(minute_sleep) >= getM){
//                        handler.sendEmptyMessage(0);
//                    }
//                }
//            }
            try {
//                    Thread.sleep(1000 * 60 * 60 * 1); //3시간

                } catch (Exception e) {
                    }
                }
            }


    }
