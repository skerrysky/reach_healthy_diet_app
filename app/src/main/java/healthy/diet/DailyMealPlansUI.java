package healthy.diet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DailyMealPlansUI extends AppCompatActivity {

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_meal_plans_ui);

        Button viewDailyMealPlansButton = findViewById(R.id.viewDailyMealPlansButtonID);
        Button createDailyMealPlansButton = findViewById(R.id.createDailyMealPlansButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        myDb = new DatabaseHelper(this);

        // Set click listener for viewDailyMealPlans button
        viewDailyMealPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DataAndOperations.DailyMealPlan> dailyMealPlans = myDb.getAllMealPlans();

                if (dailyMealPlans.isEmpty()) {
                    showDetails("Error", "No daily meal plans available.");
                } else {
                    StringBuilder mealPlanDetails = new StringBuilder();
                    for (DataAndOperations.DailyMealPlan mealPlan : dailyMealPlans) {
                        mealPlanDetails.append("Meal Plan Name: ").append(mealPlan.getPlanName()).append("\n");
                        mealPlanDetails.append("Total Calories: ").append(mealPlan.getTotalCalories()).append("\n");
                        mealPlanDetails.append("Carbon Percent: ").append(mealPlan.getCarbonPercent()).append("\n");
                        mealPlanDetails.append("Protein Percent: ").append(mealPlan.getProteinPercent()).append("\n");
                        mealPlanDetails.append("Fat Percent: ").append(mealPlan.getFatPercent()).append("\n\n");
                    }

                    showDetails("Daily Meal Plan Information", mealPlanDetails.toString());
                }
            }
        });

        // Set click listener for createDailyMealPlans button
        createDailyMealPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyMealPlansUI.this, DailyMealPlansUI_EnterNewPlan.class);
                startActivity(intent);
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyMealPlansUI.this, MainActivity.class);
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