package com.example.firebaseregister2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Test4CalenderActivity extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private CustomAdapter2 adapter2;
    private ArrayList<Kangwon> kList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // 선택한 날짜를 필터링하기 위한 변수 (연도, 월, 일)
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        kList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Kangwon");

        // 이전 활동이나 다른 곳에서 선택한 날짜를 가져옵니다.
        selectedYear = 2022;
        selectedMonth = 3;
        selectedDay = 4;

        Query query = databaseReference.orderByChild("date").equalTo(selectedYear + "_" + selectedMonth + "_" + selectedDay);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Kangwon kangwon = snapshot.getValue(Kangwon.class);
                    kList.add(kangwon);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("test1", databaseError.getMessage());
            }
        });

        adapter2 = new CustomAdapter2(kList, this);
        recyclerView2.setAdapter(adapter2);
    }
}