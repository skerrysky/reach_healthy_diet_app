package healthy.diet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExpectedDailyCaloricIntakeUI extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expected_daily_caloric_intake_ui);


        myDb = new DatabaseHelper(this);

        Button viewExpectedDailyCaloricIntakeButton = findViewById(R.id.viewExpectedDailyCaloricIntakeButton);
        Button enterExpectedDailyCaloricIntakeButton = findViewById(R.id.enterExpectedDailyCaloricIntakeButton);
        Button editExpectedDailyCaloricIntakeButton = findViewById(R.id.editExpectedDailyCaloricIntakeButton);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButton);

        // Set click listener for view button
        viewExpectedDailyCaloricIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataAndOperations.ExpectedDailyCaloricIntake expectedDailyCaloricIntake = myDb.getExpectedDailyCaloricIntake();

                if (expectedDailyCaloricIntake != null) {
                    String message = "Expected Minimum Value: " + expectedDailyCaloricIntake.getExpectedMinimumValue() + "\n" +
                            "Expected Maximum Value: " + expectedDailyCaloricIntake.getExpectedMaximumValue();
                    showDetails("Expected Daily Caloric Intake", message);
                } else {
                    showDetails("Error", "No expected daily caloric intake set.");
                }
            }
        });

        // Set click listener for enter button
        enterExpectedDailyCaloricIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpectedDailyCaloricIntakeUI.this, ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.class);
                intent.putExtra("titleText", "Enter Expected Daily Caloric Intake");
                startActivity(intent);
            }
        });

        // Set click listener for edit button
        editExpectedDailyCaloricIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataAndOperations.ExpectedDailyCaloricIntake expectedDailyCaloricIntake = myDb.getExpectedDailyCaloricIntake();

                if (expectedDailyCaloricIntake != null) {
                    Intent intent = new Intent(ExpectedDailyCaloricIntakeUI.this, ExpectedDailyCaloricIntakeUI_EnterExpectedIntake.class);
                    intent.putExtra("titleText", "Edit Expected Daily Caloric Intake");
                    intent.putExtra("expected Minimum Value", expectedDailyCaloricIntake.getExpectedMinimumValue());
                    intent.putExtra("expected Maximum Value", expectedDailyCaloricIntake.getExpectedMaximumValue());
                    startActivity(intent);
                } else {
                    showDetails("Error", "No expected daily caloric intake set.");
                }
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpectedDailyCaloricIntakeUI.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showDetails(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        // Add a button to close the dialog
        builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the dialog
                dialog.dismiss();
            }
        });
        builder.show();
    }
}