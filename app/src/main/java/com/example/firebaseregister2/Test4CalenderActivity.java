package com.example.firebaseregister2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Test4CalenderActivity extends AppCompatActivity {

    private TextView selectedDateText;
    private CalendarView calendarView;
    private EditText memoEditText;
    private Button saveButton;

    private Map<String, String> memoMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test4_calender);

        // 뷰 초기화
        initView();

        // 저장된 일정 로드
        memoMap = new HashMap<>();
        loadMemo();
// 캘린더 날짜 변경 시 이벤트 처리
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택한 날짜 정보를 가져와서 TextView에 표시
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                selectedDateText.setText(selectedDate);

                // 선택한 날짜에 저장된 메모가 있다면 EditText에 표시
                String memo = memoMap.get(selectedDate);
                if (memo != null) {
                    memoEditText.setText(memo);
                } else {
                    memoEditText.setText("");
                }
            }
        });

        // 저장 버튼 클릭 시 이벤트 처리
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 날짜와 메모 내용을 가져와서 저장
                String selectedDate = selectedDateText.getText().toString();
                String memo = memoEditText.getText().toString();

                if (selectedDate.isEmpty()) {
                    Toast.makeText(Test4CalenderActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                memoMap.put(selectedDate, memo);
                saveMemo();

                Toast.makeText(Test4CalenderActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 저장된 일정 보여주기
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        String memo = memoMap.get(today);
        if (memo != null) {
            selectedDateText.setText(today);
            memoEditText.setText(memo);
        }
    }

    // 뷰 초기화 메서드
    private void initView() {
        selectedDateText = findViewById(R.id.selected_date_text);
        calendarView = findViewById(R.id.calendar_view);
        memoEditText = findViewById(R.id.memo_edit_text);
        saveButton = findViewById(R.id.save_button);
    }

    // 일정을 저장하는 메서드
    private void saveMemo() {
        // 현재 시간을 키 값으로 사용
        String key = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        String memo = memoEditText.getText().toString();

        memoMap.put(key, memo);

        // SharedPreferences를 사용하여 저장
        getSharedPreferences("memo", MODE_PRIVATE).edit().putString(key, memo).apply();
    }

    // 저장된 일정을 불러오는 메서드
    private void loadMemo() {
        // SharedPreferences를 사용하여 불러옴
        Map<String, ?> savedData = getSharedPreferences("memo", MODE_PRIVATE).getAll();

        for (String key : savedData.keySet()) {
            memoMap.put(key, savedData.get(key).toString());
        }
    }
}
