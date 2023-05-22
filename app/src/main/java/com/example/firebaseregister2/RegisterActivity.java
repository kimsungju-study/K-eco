package com.example.firebaseregister2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;         // 파이어베이스 인증
    private DatabaseReference mDatabaseRef;    // 실시간 데이터베이스 인증
    private EditText mEtEmail, mEtPwd, mEtGroup;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtGroup = findViewById(R.id.et_group);

        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strAffiliation = mEtGroup.getText().toString(); // Get the affiliation value from user input

                // Perform Firebase Auth registration
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    UserAccount account = new UserAccount();
                                    account.setIdToken(firebaseUser.getUid());
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPwd);
                                    account.setAffiliation(strAffiliation); // Set the affiliation value

                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                    Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
