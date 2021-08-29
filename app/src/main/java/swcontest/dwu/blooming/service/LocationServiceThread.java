package swcontest.dwu.blooming.service;

import android.os.Handler;

public class LocationServiceThread extends Thread {

    Handler handler;
    boolean isRun = true;

    public LocationServiceThread(Handler handler) { this.handler = handler; }

    public void stopForever() {
        synchronized (this) { this.isRun = false; }
    }

    public void run() {
        // 반복적으로 수행할 작업을 한다.
        while (isRun) {
            handler.sendEmptyMessage( 0 );
            try {
                Thread.sleep( 1000 * 60 * 3 ); // 3분 간격
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
