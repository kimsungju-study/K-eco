package com.example.firebaseregister2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private Button btn_test2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, test2.class) ;

                startActivity(intent) ;
            }
        });

        Button button2 = (Button) findViewById(R.id.button2) ;
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, test1.class) ;

                startActivity(intent) ;
            }
        });

        Button button3 = (Button) findViewById(R.id.button3) ;
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, test3.class) ;

                startActivity(intent) ;
            }
        });

        Button button4 = (Button) findViewById(R.id.button4) ;
        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Test4CalenderActivity.class) ;

                startActivity(intent) ;
            }
        });


        recyclerView = findViewById(R.id.recyclerView);//아이디 연결
        recyclerView.setHasFixedSize(true);//성능강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//User 객체 담는 배열리스트-> 어댑터 쪽으로

        database = FirebaseDatabase.getInstance();//파이어베이스 데이터 연동

        databaseReference = database.getReference("User"); //데이터베이스 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터 베이스 받아오는 부분
                arrayList.clear();// 기존 배열 리스트가 존재하지 못하게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){// 반복문을 통하여 데이터list 추출
                    User user = snapshot.getValue(User.class);//만들어둔 user 객체에 데이터 삽입
                    arrayList.add(user);// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰에 보낼 준비
                }
                adapter.notifyDataSetChanged();// 리스트저장 후 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    //데이터 가져오는 중 에러코드
                Log.e("MainActivity", String.valueOf(databaseError.toException()));//에러 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);//리사이클 뷰에 어탭터 연결

    }
/*
    public void goToTest1(View view) {
        Intent intent = new Intent(this, test1.class);
        startActivity(intent);
    }

    public void goToTest2(View view) {
        Intent intent = new Intent(this, test2.class);
        startActivity(intent);
    }
*/



}