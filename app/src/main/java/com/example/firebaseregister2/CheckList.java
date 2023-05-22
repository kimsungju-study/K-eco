package com.example.firebaseregister2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {

    private CheckBox checkBox1, checkBox2, checkBox3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> checkedItems = new ArrayList<>();
                if (checkBox1.isChecked()) {
                    checkedItems.add(checkBox1.getText().toString());
                }
                if (checkBox2.isChecked()) {
                    checkedItems.add(checkBox2.getText().toString());
                }
                if (checkBox3.isChecked()) {
                    checkedItems.add(checkBox3.getText().toString());
                }

                if (checkedItems.isEmpty()) {
                    Toast.makeText(CheckList.this, "체크된 항목이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    String message = "체크된 항목: " + checkedItems.toString();
                    Toast.makeText(CheckList.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
