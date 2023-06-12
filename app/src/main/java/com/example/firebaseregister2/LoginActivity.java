package com.example.firebaseregister2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;         //파이어 베이스 인증
    private EditText mEtEmail , mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        //실시간 데이터 베이스 인증

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        Button mBtnRegister = findViewById(R.id.btn_register);
        Button mBtnLogin = findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(v -> {
            String strEmail = mEtEmail.getText().toString();
            String strPwd = mEtPwd.getText().toString();

            if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPwd)) {
                Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // 로그인 성공시
                        Intent intent = new Intent(LoginActivity.this, CalenderActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mBtnRegister.setOnClickListener(v -> {
            Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

}