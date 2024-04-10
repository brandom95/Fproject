package com.project.fproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BucketApiActivity extends AppCompatActivity implements ApiTask.ApiCallback {

    private EditText budgetEditText;
    private TextView apiResponseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_api);

        Button apiButton = findViewById(R.id.apiButton);
        apiResponseTextView = findViewById(R.id.apiResponseTextView);
        budgetEditText = findViewById(R.id.budgetEditText);

        apiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get budget value from EditText
                String budgetText = budgetEditText.getText().toString();
                if (!budgetText.isEmpty()) {
                    double budget = Double.parseDouble(budgetText);
                    if (budget < 1000) {
                        apiResponseTextView.setText("Sorry, you're too tight.");
                    } else {
                        // Initiate the API call only if budget is greater or equal to 1000
                        new ApiTask(BucketApiActivity.this).execute();
                    }
                }
            }
        });
    }

    @Override
    public void onSuccess(String result) {
        // Handle successful response
        apiResponseTextView.setText(result); // Set the API response text to the TextView
    }

    @Override
    public void onError(String error) {
        // Handle error
        apiResponseTextView.setText("Error: " + error); // Display error message in TextView
    }
}
