// CalenderActivity.java

package com.example.firebaseregister2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class CalenderActivity extends AppCompatActivity {

    private TextView dataTextView;

    private FirebaseDatabase database;
    private String selectedAffiliation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView txtAffiliation = findViewById(R.id.txt_affiliation);

        // 현재 로그인한 사용자 가져오기
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Firebase 실시간 데이터베이스에서 UserAccount 객체 가져오기
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("UserAccount")
                    .child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                        if (userAccount != null) {
                            selectedAffiliation = userAccount.getAffiliation();
                            txtAffiliation.setText(selectedAffiliation);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("CalenderActivity", "Failed to retrieve affiliation: " + databaseError.getMessage());
                }
            });
        }

        // Initialize Firebase database
        database = FirebaseDatabase.getInstance();

        // 뷰 찾기
        CalendarView calendarView = findViewById(R.id.calendarView);
        dataTextView = findViewById(R.id.dataTextView);
        Button restApiButton = findViewById(R.id.add_button2);
        Button restApiButton2 = findViewById(R.id.add_button);

        // 선택된 날짜를 가져오기 위해 OnDateChangeListener 설정
        calendarView.setOnDateChangeListener((calendarView1, year, month, dayOfMonth) -> {
            // 선택된 날짜를 형식에 맞게 포맷팅
            String selectedDate = String.format(Locale.KOREA, "%04d_%01d_%01d", year, month + 1, dayOfMonth);

            // 선택된 날짜와 소속을 기준으로 Firebase 데이터베이스에서 쿼리하기
            DatabaseReference databaseReference = database.getReference(selectedAffiliation);
            databaseReference.orderByChild("date").equalTo(selectedDate)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // 선택된 날짜에 해당하는 데이터가 있을 경우
                                // 데이터 가져와서 표시하기
                                StringBuilder dataBuilder = new StringBuilder();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Kangwon kangwon = snapshot.getValue(Kangwon.class);
                                    if (kangwon != null) {
                                        dataBuilder
                                                .append("덮밥메뉴: ").append(kangwon.getData_bak_menu_1()).append("\n")
                                                .append("덮밥티켓 수량: ").append(kangwon.getData_bak_tickets()).append("\n")
                                                .append("백반메뉴(밥) : ").append(kangwon.getData_dup_menu_1()).append("\n")
                                                .append("백반메뉴(국): ").append(kangwon.getData_dup_menu_2()).append("\n")
                                                .append("백반메뉴(메인반찬): ").append(kangwon.getData_dup_menu_3()).append("\n")
                                                .append("백반메뉴(저녁): ").append(kangwon.getData_dup_menu_4()).append("\n")
                                                .append("백반티켓 수량: ").append(kangwon.getData_dup_tickets()).append("\n")
                                                .append("특선메뉴(점심): ").append(kangwon.getData_sp_menu_1()).append("\n")
                                                .append("특선메뉴(저녁): ").append(kangwon.getData_sp_menu_2()).append("\n")
                                                .append("특선티켓 수량: ").append(kangwon.getData_sp_tickets()).append("\n")
                                                .append("음식물쓰레기 배출량: ").append(kangwon.getFoodwaste()).append("\n\n");
                                    }
                                }
                                dataTextView.setText(dataBuilder.toString());
                            } else {
                                // 선택된 날짜에 해당하는 데이터가 없을 경우
                                dataTextView.setText("데이터가 없습니다.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("CalenderActivity", "Error retrieving data: " + databaseError.getMessage());
                        }
                    });
        });

        restApiButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalenderActivity.this, RestApi3.class);
            startActivity(intent);
        });

        restApiButton2.setOnClickListener(v -> {
            Intent intent = new Intent(CalenderActivity.this, RestApi4.class);
            startActivity(intent);
        });
    }
}
