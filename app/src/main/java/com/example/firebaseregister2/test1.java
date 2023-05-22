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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class test1 extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private CustomAdapter2 adapter2;
    private List<Kangwon> kList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

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

        // Retrieve data from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Kangwon kangwon = snapshot.getValue(Kangwon.class);
                    kList.add(kangwon);
                }
                adapter2.notifyDataSetChanged();

                // Process and store data in the calendar
                storeDataInCalendar(kList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("test1", databaseError.getMessage());
            }
        });

        adapter2 = new CustomAdapter2((ArrayList<Kangwon>) kList, this);
        recyclerView2.setAdapter(adapter2);
    }

    private void storeDataInCalendar(List<Kangwon> dataList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.US);
        Calendar calendar = Calendar.getInstance();

        for (Kangwon kangwon : dataList) {
            try {
                Date date = dateFormat.parse(kangwon.getDate());
                calendar.setTime(date);

                // Store the data in the calendar based on the date
                // You can implement your own logic here

                // Example: Print the data for each date
                System.out.println("Date: " + dateFormat.format(date));
                System.out.println("Menu 1: " + kangwon.getData_bak_menu_1());
                System.out.println("Menu 2: " + kangwon.getData_dup_menu_1());
                System.out.println("Menu 3: " + kangwon.getData_dup_menu_2());
                System.out.println("Menu 4: " + kangwon.getData_dup_menu_3());
                System.out.println("Tickets: " + kangwon.getData_bak_tickets());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}