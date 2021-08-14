package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import swcontest.dwu.blooming.db.DiaryDBHelper;
import swcontest.dwu.blooming.db.DiaryDBManager;
import swcontest.dwu.blooming.dto.DiaryDto;

public class KeepDiaryActivity extends AppCompatActivity {

    DiaryDto diaryDto;

    TextView etDate;
    EditText etDiary;
   //DiaryDBHelper helper;
    DiaryDBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_diary);
        diaryDto = (DiaryDto)getIntent().getSerializableExtra("diaryDto");

        etDate = findViewById(R.id.dateText);
        etDiary = findViewById(R.id.diaryText);

        Intent getIntent = getIntent();
        int year = getIntent.getExtras().getInt("year");
        int month = getIntent.getExtras().getInt("month");
        int day = getIntent.getExtras().getInt("day");

        //선택날짜 표시
        etDate.setText(String.valueOf(year)+"년 " + String.valueOf(month)+"월 "+ String.valueOf(day)+"일 ");
        //일기 쓴거 표시
        //etDiary.setText(diaryDto.getDiary());

        //helper = new DiaryDBHelper(this);
        manager = new DiaryDBManager(this);
        //manager.getDiary();
    }
    public void OnClick(View v){

        switch (v.getId()) {
            case R.id.deleteDiary:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.comitDiary:
                boolean result = manager.addNewDiary(
                        new DiaryDto(etDate.getText().toString(),etDiary.getText().toString()));

                if(result){//정상일경우
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("diaryDto", etDiary.toString());
                    setResult(RESULT_OK,resultIntent);
                    finish();

                }else{//이상
                    Toast.makeText(this, "다이어리 추가 실패!", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }
}