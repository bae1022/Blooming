package swcontest.dwu.blooming;

import android.os.Handler;

public class LocationActivityThread extends Thread  {
    Handler handler;
    boolean isRun = true;

    public LocationActivityThread(Handler handler) {
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
                Thread.sleep(1000 * 60 * 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
