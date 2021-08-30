package swcontest.dwu.blooming.service;


import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import java.util.ArrayList;

import swcontest.dwu.blooming.db.DailyMemoDBHelper;
import swcontest.dwu.blooming.db.LocationDBHelper;
import swcontest.dwu.blooming.db.UserDBHelper;
import swcontest.dwu.blooming.dto.LocationDto;

public class DailyMemoServiceThread extends Thread{

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

    public void run(){
        while(isRun){
            handler.sendEmptyMessage(0);

            try{
                Thread.sleep(3000); //3분 간격, 시간 설정 및 수정 필요
            } catch (Exception e){

            }
        }
    }


}
