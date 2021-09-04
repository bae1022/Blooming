package swcontest.dwu.blooming;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiagnosisActivity extends AppCompatActivity {

    CheckBox cb1 ,cb2 ,cb3 , cb4 ,cb5 , cb6 ,cb7 ,cb8,cb9,cb10, cb11,cb12, cb13 , cb14;

    Dialog dialog;
    TextView result1;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);
        cb8 = findViewById(R.id.cb8);
        cb9 = findViewById(R.id.cb9);
        cb10 = findViewById(R.id.cb10);
        cb11 = findViewById(R.id.cb11);
        cb12 = findViewById(R.id.cb12);
        cb13 = findViewById(R.id.cb13);
        cb14 = findViewById(R.id.cb14);

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        cb14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckedOrNot(b);
                Log.d("check", count + "개");
            }
        });

        dialog = new Dialog(DiagnosisActivity.this);

        dialog.setContentView(R.layout.activity_diagnosis_result);

        result1 = dialog.findViewById(R.id.tvResult1);
    }

    private void isCheckedOrNot(boolean isChecked) {
        if (isChecked) {
            count++;
        } else {
            if (count > 0) {
                count--;
            }
        }
    }

    public void showDialog(){
        dialog.show();

        Button no = dialog.findViewById(R.id.btnNo);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheck:
                Log.d("check", count + "개");

                if(count > 5){
                    result1.setText("치매 위험단계로 의심됩니다.\n\n가까운 보건소나 치매센터에 방문하셔서 현재 상태에 대한 상담을 받는것이 좋겠습니다.");
                }else if(count > 10){
                    result1.setText("현재 치매일 가능성이 높습니다.\n\n가까운 보건소나 치매센터에 방문하셔서 치매조기선별검사를 받으시길 바랍니다.");
                }

                showDialog();
                break;
        }
    }
}
