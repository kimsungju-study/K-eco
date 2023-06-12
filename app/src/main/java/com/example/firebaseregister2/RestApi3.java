package com.example.firebaseregister2;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestApi3 extends AppCompatActivity {

    private TextView responseTextView;
    private EditText dateEditText;

    private EditText dayEditText;

    private EditText dupMenuEditText;
    private EditText dupMenu2EditText;
    private EditText dupMenu3EditText;
    private EditText dupMenu4EditText;
    private EditText dupMenu5EditText;
    private EditText dupMenu6EditText;
    private EditText spMenu1EditText;
    private EditText spMenu2EditText;
    private EditText bakMenu1EditText;
    private Button sendRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restapi3);

        responseTextView = findViewById(R.id.sendRequestButton);
        dateEditText = findViewById(R.id.dateEditText);
        dayEditText = findViewById(R.id.dayEditText);

        dupMenuEditText = findViewById(R.id.dupMenuEditText);
        dupMenu2EditText = findViewById(R.id.dupMenuEditText2);
        dupMenu3EditText = findViewById(R.id.dupMenuEditText3);
        dupMenu4EditText = findViewById(R.id.dupMenuEditText4);
        dupMenu5EditText = findViewById(R.id.dupMenuEditText5);
        dupMenu6EditText = findViewById(R.id.dupMenuEditText6);
        spMenu1EditText = findViewById(R.id.spMenu1EditText);
        spMenu2EditText = findViewById(R.id.spMenu2EditText);
        bakMenu1EditText = findViewById(R.id.bakMenu1EditText);
        sendRequestButton = findViewById(R.id.sendRequestButton);

        sendRequestButton.setOnClickListener(v -> {
            // Retrieve user input
            String date = dateEditText.getText().toString();
            String day = dayEditText.getText().toString();
            String dupMenu = dupMenuEditText.getText().toString();
            String dupMenu2 = dupMenu2EditText.getText().toString();
            String dupMenu3 = dupMenu3EditText.getText().toString();
            String dupMenu4 = dupMenu4EditText.getText().toString();
            String dupMenu5 = dupMenu5EditText.getText().toString();
            String dupMenu6 = dupMenu6EditText.getText().toString();
            String spMenu1 = spMenu1EditText.getText().toString();
            String spMenu2 = spMenu2EditText.getText().toString();
            String bakMenu1 = bakMenu1EditText.getText().toString();

            // Validate user input if needed

            // Create JSON object
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("date", date);
                jsonObject.put("day", day);
                jsonObject.put("data_dup_menu_1", dupMenu);
                jsonObject.put("data_dup_menu_2", dupMenu2);
                jsonObject.put("data_dup_menu_3", dupMenu3);
                jsonObject.put("data_dup_menu_4", dupMenu4);
                jsonObject.put("data_dup_menu_5", dupMenu5);
                jsonObject.put("data_dup_menu_6", dupMenu6);
                jsonObject.put("data_sp_menu_1", spMenu1);
                jsonObject.put("data_sp_menu_2", spMenu2);
                jsonObject.put("data_bak_menu_1", bakMenu1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Execute API request
            new ApiRequestTask().execute(jsonObject);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class ApiRequestTask extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            try {
                // API endpoint URL
                URL url = new URL("http://183.123.217.182:5000/predict");

                // HttpURLConnection settings
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setChunkedStreamingMode(0);  // Enable chunked streaming mode

                // Get the JSON object
                JSONObject jsonObject = jsonObjects[0];

                // Send POST data
                connection.setDoOutput(true);
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                String jsonData = String.valueOf(jsonObject);
                byte[] data = jsonData.getBytes(StandardCharsets.UTF_8);
                int chunkSize = 1024;
                int offset = 0;
                int length = Math.min(data.length - offset, chunkSize);

                while (length > 0) {
                    outputStream.write(data, offset, length);
                    offset += length;
                    length = Math.min(data.length - offset, chunkSize);
                }

                outputStream.flush();
                outputStream.close();

                // Check response code
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response data
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Return response data
                    return response.toString();
                } else {
                    // Return error message along with the response code
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Return error message
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                int predictedBaekban = (int) Math.round(jsonResponse.getDouble("predictedBaekban"));
                int predictedSpecial = (int) Math.round(jsonResponse.getDouble("predictedSpecial"));
                int predictedBowl = (int) Math.round(jsonResponse.getDouble("predictedBowl"));

                String displayText = "백반 예상 수요량: " + predictedBaekban + "\n" +
                        "특식 예상 수요량: " + predictedSpecial + "\n" +
                        "덮밥 예상 수요량: " + predictedBowl;

                responseTextView.setText(displayText);
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing error
                responseTextView.setText(
                                         "접속 불가" +
                                                 "\n");
            }
        }
    }
}
