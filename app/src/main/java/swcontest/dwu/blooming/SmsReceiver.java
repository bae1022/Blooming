package swcontest.dwu.blooming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import swcontest.dwu.blooming.db.CardDBManager;
import swcontest.dwu.blooming.dto.CardDto;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private CardDBManager dbManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbManager = new CardDBManager(context);

        // SMS_RECEIVED에 대한 액션일때 실행
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Log.d(TAG, "onReceiver() 호출"); // Bundle을 이용해서 메세지 내용을 가져옴
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages = parseSmsMessage(bundle);
            // 메세지가 있을 경우 내용을 로그로 출력해 봄
            if (messages.length > 0) {
                // 메세지의 내용을 가져옴
//                String sender = messages[0].getOriginatingAddress();
                String contents = messages[0].getMessageBody().toString();
                String[] split = contents.split("\n");
                if(split[0].equals("[Web발신]")){
                    if(split[1].startsWith("하나") || split[1].startsWith("NH") || split[1].startsWith("삼성") || split[1].startsWith("롯데") || split[1].startsWith("신한")
                        || split[1].startsWith("KB") || split[1].startsWith("우리") || split[1].startsWith("MG") || split[1].startsWith("BC") || split[1].startsWith("IBK")){
                        String title = split[5];
                        String price = split[3];
                        String[] cut = price.split(" ");
                        price = cut[0];
                        String receivedDate = split[4];
//                        String receivedDate = new Date(messages[0].getTimestampMillis());
                        // 로그를 찍어보는 과정이므로 생략해도 됨
                        Log.d(TAG, "title :" + title);
                        Log.d(TAG, "price : " + price);
                        Log.d(TAG, "receivedDate : " + receivedDate);
                        // 액티비티로 메세지의 내용을 전달해줌
//                        sendToActivity(context, sender, contents, receivedDate);
                      saveMessage(context, title, price, receivedDate);
                    }

                }

            }
        }
    }

    private void saveMessage(Context context, String title, String price, String receivedDate){
        CardDto card = new CardDto(title, receivedDate, price);
        boolean result = dbManager.addNewCard(card);

        if (result) {
            Toast.makeText(context, "문자 저장!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "저장 실패!", Toast.LENGTH_SHORT).show();
        }
    }


    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];
        for (int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }
}