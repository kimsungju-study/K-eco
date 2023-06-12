// RestApi4.java

package com.example.firebaseregister2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RestApi4 extends AppCompatActivity {

    private static final String TAG = "RestApi4";

    private EditText editTextDataBakMenu1;
    private EditText editTextDataBakTickets;
    private EditText editTextDataDupMenu1;
    private EditText editTextDataDupMenu2;
    private EditText editTextDataDupMenu3;
    private EditText editTextDataDupMenu4;
    private EditText editTextDataDupTickets;
    private EditText editTextDataSpMenu1;
    private EditText editTextDataSpMenu2;

    private EditText editTextDataSpTickets;
    private EditText editTextDate;
    private EditText editTextFoodWaste;
    private String selectedAffiliation;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        TextView txtAffiliation = findViewById(R.id.txt_affiliation);

        // 현재 로그인한 사용자 가져오기
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Firebase 실시간 데이터베이스에서 UserAccount 객체 가져오기
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("UserAccount")
                    .child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                        if (userAccount != null) {
                            selectedAffiliation = userAccount.getAffiliation();
                            txtAffiliation.setText(selectedAffiliation);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("CalenderActivity", "Failed to retrieve affiliation: " + databaseError.getMessage());
                }
            });
        }

        editTextDataBakMenu1 = findViewById(R.id.editText_data_bak_menu_1);
        editTextDataBakTickets = findViewById(R.id.editText_data_bak_tickets);
        editTextDataDupMenu1 = findViewById(R.id.editText_data_dup_menu_1);
        editTextDataDupMenu2 = findViewById(R.id.editText_data_dup_menu_2);
        editTextDataDupMenu3 = findViewById(R.id.editText_data_dup_menu_3);
        editTextDataDupMenu4 = findViewById(R.id.editText_data_dup_menu_4);
        editTextDataDupTickets = findViewById(R.id.editText_data_dup_tickets);
        editTextDataSpMenu1 = findViewById(R.id.editText_data_sp_menu_1);
        editTextDataSpMenu2 = findViewById(R.id.editText_data_sp_menu_2);
        editTextDataSpTickets = findViewById(R.id.editText_data_sp_tickets);
        editTextDate = findViewById(R.id.editText_date);
        editTextFoodWaste = findViewById(R.id.editText_foodwaste);
        buttonSave = findViewById(R.id.button_save);

        buttonSave.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        int dataBakTickets = Integer.parseInt(editTextDataBakTickets.getText().toString());
        int dataDupTickets = Integer.parseInt(editTextDataDupTickets.getText().toString());
        int dataSpTickets = Integer.parseInt(editTextDataSpTickets.getText().toString());
        int foodWaste = Integer.parseInt(editTextFoodWaste.getText().toString());

        String dataBakMenu1 = editTextDataBakMenu1.getText().toString();

        String dataDupMenu1 = editTextDataDupMenu1.getText().toString();
        String dataDupMenu2 = editTextDataDupMenu2.getText().toString();
        String dataDupMenu3 = editTextDataDupMenu3.getText().toString();
        String dataDupMenu4 = editTextDataDupMenu4.getText().toString();

        String dataSpMenu1 = editTextDataSpMenu1.getText().toString();
        String dataSpMenu2 = editTextDataSpMenu2.getText().toString();
        String date = editTextDate.getText().toString();


        if (selectedAffiliation != null) {
            DatabaseReference kangwonRef = FirebaseDatabase.getInstance().getReference(selectedAffiliation).push();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("data_bak_menu_1", dataBakMenu1);
            dataMap.put("data_bak_tickets", dataBakTickets);
            dataMap.put("data_dup_menu_1", dataDupMenu1);
            dataMap.put("data_dup_menu_2", dataDupMenu2);
            dataMap.put("data_dup_menu_3", dataDupMenu3);
            dataMap.put("data_dup_menu_4", dataDupMenu4);
            dataMap.put("data_dup_tickets", dataDupTickets);
            dataMap.put("data_sp_menu_1", dataSpMenu1);
            dataMap.put("data_sp_menu_2", dataSpMenu2);
            dataMap.put("data_sp_tickets", dataSpTickets);
            dataMap.put("date", date);
            dataMap.put("foodwaste", foodWaste);

            kangwonRef.setValue(dataMap)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "저장이 성공적으로 완료되었습니다.");
                        Toast.makeText(RestApi4.this, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "데이터 저장에 실패했습니다: " + e.getMessage());
                        Toast.makeText(RestApi4.this, "데이터 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "소속을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextDataBakMenu1.setText("");
        editTextDataBakTickets.setText("");
        editTextDataDupMenu1.setText("");
        editTextDataDupMenu2.setText("");
        editTextDataDupMenu3.setText("");
        editTextDataDupMenu4.setText("");
        editTextDataDupTickets.setText("");
        editTextDataSpMenu1.setText("");
        editTextDataSpMenu2.setText("");
        editTextDate.setText("");
        editTextFoodWaste.setText("");
    }
}
