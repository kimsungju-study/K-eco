package com.example.firebaseregister2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtAffiliation = findViewById(R.id.txt_affiliation);

        // Get the currently logged-in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Retrieve the UserAccount object from Firebase Realtime Database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("UserAccount")
                    .child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                        if (userAccount != null) {
                            String affiliation = userAccount.getAffiliation();
                            txtAffiliation.setText(affiliation);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("MainActivity", "Failed to retrieve affiliation: " + databaseError.getMessage());
                }
            });
        }



        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalenderActivity.class);

            startActivity(intent);
        });
    }
}