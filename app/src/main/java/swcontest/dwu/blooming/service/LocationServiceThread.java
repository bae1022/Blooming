package swcontest.dwu.blooming.service;

import android.os.Handler;

import static swcontest.dwu.blooming.MainActivity.period;

public class LocationServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public LocationServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        while (isRun) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000 * 60 * period);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
