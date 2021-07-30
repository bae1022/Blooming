package swcontest.dwu.blooming.service;


import android.os.Handler;

public class DailyMemoServiceThread extends Thread{

    Handler handler;
    boolean isRun = true;

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
                Thread.sleep(30000); //3분 간격, 시간 설정 및 수정 필요
            } catch (Exception e){

            }
        }
    }
}
