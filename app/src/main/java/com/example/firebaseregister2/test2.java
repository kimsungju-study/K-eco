package com.example.firebaseregister2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class test2 extends AppCompatActivity {

    private LinearLayout llMenuContainer;
    private Button btnAdd;
    private EditText etMenuInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        llMenuContainer = findViewById(R.id.ll_menu_container);
        btnAdd = findViewById(R.id.btn_add);

        etMenuInput = findViewById(R.id.et_menu_input);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuInput = etMenuInput.getText().toString(); // EditText에서 입력된 텍스트를 가져옵니다.
                addMenuInput(menuInput); // addMenuInput() 메서드에 전달합니다.
            }
        });
    }

    private void addMenuInput(String menuType) {
        LinearLayout llMenuInput = new LinearLayout(this);
        llMenuInput.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);
        llMenuInput.setLayoutParams(params);

        TextView tvMenuType = new TextView(this);
        tvMenuType.setText(menuType);
        tvMenuType.setTextSize(18);
        tvMenuType.setTextColor(getResources().getColor(R.color.black));
        tvMenuType.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        EditText etMenuName = new EditText(this);
        etMenuName.setHint("메뉴를 써주세요");
        etMenuName.setTextSize(18);
        etMenuName.setTextColor(getResources().getColor(R.color.black));
        etMenuName.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2
        ));

        Button btnAddInput = new Button(this);
        btnAddInput.setText("추가");
        btnAddInput.setTextSize(18);
        btnAddInput.setTextColor(getResources().getColor(R.color.white));
        btnAddInput.setBackgroundResource(R.drawable.btn_background);
        btnAddInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        btnAddInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenuInput(menuType);
            }
        });

        llMenuInput.addView(tvMenuType);
        llMenuInput.addView(etMenuName);
        llMenuInput.addView(btnAddInput);

        llMenuContainer.addView(llMenuInput);
    }
}