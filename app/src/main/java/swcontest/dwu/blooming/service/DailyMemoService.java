package swcontest.dwu.blooming.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import swcontest.dwu.blooming.DailyMemoActivity;
import swcontest.dwu.blooming.R;

public class DailyMemoService extends Service {

    NotificationManager noti_m;
    DailyMemoServiceThread thread;
    Notification noti;

    public DailyMemoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    //백그라운드에서 실행되는 동작
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        noti_m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        DailyMemoServiceHandler handler = new DailyMemoServiceHandler();
        thread = new DailyMemoServiceThread(handler);
        thread.start();

        return START_STICKY;
    }

    class DailyMemoServiceHandler extends Handler {

        public void handleMessage(Message msg){
            // Notification 출력_channel 등록 되어 있어야 함
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.channel_name);       // strings.xml 에 채널명 기록
                String description = getString(R.string.channel_description);       // strings.xml에 채널 설명 기록
                int importance = NotificationManager.IMPORTANCE_DEFAULT;    // 알림의 우선순위 지정
                NotificationChannel channel = new NotificationChannel(getString(R.string.CHANNEL_ID), name, importance);    // CHANNEL_ID 지정
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this

                NotificationManager notificationManager = getSystemService(NotificationManager.class);  // 채널 생성
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(getApplicationContext(), DailyMemoActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(DailyMemoService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.CHANNEL_ID))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("지금 뭘 하고 계신가요?")
                    .setContentText("당신의 일상을 알려주세요!")
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("기본적인 알림의 메시지 보다 더 많은 양의 내용을 알림에 표시하고자 할 때 메시지가 잘리지 않도록 함."))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            int notificationId = 100;

            notificationManager.notify(notificationId, builder.build());

            //토스트 띄우기
            Toast.makeText(DailyMemoService.this, "작동 ok", Toast.LENGTH_LONG).show();
        }
    }
}