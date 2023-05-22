package com.example.firebaseregister2;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Membership extends AppCompatActivity {

    private EditText mbName;
    private EditText mbEmail;
    private EditText mbAf;
    private EditText mbPs2;
    private TextView textView3;
    private TextView textView5;
    private TextView textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        mbName = findViewById(R.id.mb_name);
        mbEmail = findViewById(R.id.mb_email);
        mbAf = findViewById(R.id.mb_af);
        mbPs2 = findViewById(R.id.mb_ps_2);
        textView3 = findViewById(R.id.textView3);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        // Further code implementation for the activity
    }
}
