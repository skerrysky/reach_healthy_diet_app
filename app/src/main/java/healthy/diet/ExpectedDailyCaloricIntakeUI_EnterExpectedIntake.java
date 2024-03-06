package healthy.diet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.InputFilter;

import androidx.appcompat.app.AppCompatActivity;

public class ExpectedDailyCaloricIntakeUI_EnterExpectedIntake extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText expectedMinimumValueEditText;
    private EditText expectedMaximumValueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expected_daily_caloric_intake_details_ui);
        myDb = new DatabaseHelper(this);

        expectedMinimumValueEditText = findViewById(R.id.expectedMinimumValueID);
        expectedMaximumValueEditText = findViewById(R.id.expectedMaximumValueID);

        TextView screenTitle = findViewById(R.id.labelExpectedDailyCaloricIntakeScreen);
        Button saveButton = findViewById(R.id.saveButtonID);
        Button cancelButton = findViewById(R.id.cancelButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        String titleText = getIntent().getStringExtra("titleText");
        screenTitle.setText(titleText);

        if ("Edit Expected Minimum Value".equals(titleText)) {
            expectedMinimumValueEditText.setText(getIntent().getStringExtra("expectedMinimumValue"));
            expectedMaximumValueEditText.setText(getIntent().getStringExtra("expectedMaximumValue"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(expectedMinimumValueEditText.getText().toString().isEmpty() || expectedMaximumValueEditText.getText().toString().isEmpty() ))
                {
                    Log.d("mess", "didn't work");
                    double expectedMinimumValue = Double.parseDouble(expectedMinimumValueEditText.getText().toString());
                    double expectedMaximumValue = Double.parseDouble(expectedMaximumValueEditText.getText().toString());

                    if (expectedMinimumValue >= 800 && expectedMinimumValue < expectedMaximumValue && expectedMaximumValue <= 3500) {
                        boolean isUpdated = myDb.insertOrUpdateExpectedCaloricIntake(expectedMinimumValue, expectedMaximumValue);
                        if (isUpdated) {
                            Toast.makeText(ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.this, "Data updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.this, "Error updating data", Toast.LENGTH_LONG).show();
                        }

                        finish();
                    }
                } else {
                    Toast.makeText(ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.this, "Please fill in all the fields.", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.this, MainActivity.class);
                startActivity(intent);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.this, MainActivity.class);
                startActivity(intent);
            }
        });

        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
