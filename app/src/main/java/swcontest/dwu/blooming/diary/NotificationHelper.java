package swcontest.dwu.blooming.diary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import swcontest.dwu.blooming.DailyMemoActivity;
import swcontest.dwu.blooming.DailyMemoListActivity;
import swcontest.dwu.blooming.R;

public class NotificationHelper extends ContextWrapper {

    public  static final String channelID = "channelID";
    public  static final String channelNm = "channelNm";

    private NotificationManager notiManager;

    public NotificationHelper(Context base) {
        super(base);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(channelID, channelNm, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.black);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    NotificationManager getManager() {
        if(notiManager == null){
            notiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  notiManager;
    }
    public NotificationCompat.Builder getChannelNotification(){
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this,
        RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, DailyMemoListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return  new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("일기써야해요.")
                .setContentText("오늘 어때요?")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(ringtoneUri)
                .setContentIntent(pendingIntent);

    }
}
