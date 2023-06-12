package com.example.firebaseregister2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;         // 파이어베이스 인증
    private DatabaseReference mDatabaseRef;    // 실시간 데이터베이스 인증
    private EditText mEtEmail, mEtPwd, mEtGroup, mEtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Locale locale = new Locale("ko", "KR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtGroup = findViewById(R.id.et_group);
        mEtName = findViewById(R.id.et_name);

        Button mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(v -> {
            // Get the entered values
            String strEmail = mEtEmail.getText().toString();
            String strPwd = mEtPwd.getText().toString();
            String strAffiliation = mEtGroup.getText().toString();
            String strName = mEtName.getText().toString();

            // Perform Firebase Auth registration
            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd)
                    .addOnCompleteListener(RegisterActivity.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            assert firebaseUser != null;
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setAffiliation(strAffiliation);
                            account.setName(strName);
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
