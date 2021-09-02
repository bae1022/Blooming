package swcontest.dwu.blooming;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import swcontest.dwu.blooming.service.DailyMemoService;

public class AlarmDailyMemoBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = context.getString(R.string.channel_name);       // strings.xml 에 채널명 기록
                String description = context.getString(R.string.channel_description);       // strings.xml에 채널 설명 기록
                int importance = NotificationManager.IMPORTANCE_DEFAULT;    // 알림의 우선순위 지정
                NotificationChannel channel = new NotificationChannel(context.getString(R.string.CHANNEL_ID), name, importance);    // CHANNEL_ID 지정
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);  // 채널 생성
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent2 = new Intent(context.getApplicationContext(), DailyMemoActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
            NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(context, context.getString(R.string.CHANNEL_ID))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("지금 무엇을 하고 계신가요?")
                    .setContentText("당신의 일상을 알려주세요!")
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("기본적인 알림의 메시지 보다 더 많은 양의 내용을 알림에 표시하고자 할 때 메시지가 잘리지 않도록 함."))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            int notificationId = 100;

            notificationManager.notify(0, builder.build());

//        //토스트 띄우기
//        Toast.makeText(DailyMemoService.this, "작동 ok", Toast.LENGTH_LONG).show();
        }
//    }
}
