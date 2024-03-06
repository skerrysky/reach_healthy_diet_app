package healthy.diet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ComparisonResultUI extends AppCompatActivity {


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_comparison_result);


        Intent intent = getIntent();
        ArrayList<DataAndOperations.DailyMealPlan> selectedPlans = (ArrayList<DataAndOperations.DailyMealPlan>) getIntent().getSerializableExtra("something");

        if (selectedPlans != null) {
            // Get checked plans from SelectPlans
            DataAndOperations.DailyMealPlan plan1 = selectedPlans.get(0);
            DataAndOperations.DailyMealPlan plan2 = selectedPlans.get(1);
            Log.d("ComparisonDebug", "Plan 1 - Carbon Percent: " + plan1.getCarbonPercent());
            Log.d("ComparisonDebug", "Plan 1 - Protein Percent: " + plan1.getProteinPercent());
            Log.d("ComparisonDebug", "Plan 1 - Fat Percent: " + plan1.getFatPercent());
            Log.d("ComparisonDebug", "Plan 1 - Total Calories: " + plan1.getTotalCalories());

            Log.d("ComparisonDebug", "Plan 2 - Carbon Percent: " + plan2.getCarbonPercent());
            Log.d("ComparisonDebug", "Plan 2 - Protein Percent: " + plan2.getProteinPercent());
            Log.d("ComparisonDebug", "Plan 2 - Fat Percent: " + plan2.getFatPercent());
            Log.d("ComparisonDebug", "Plan 2 - Total Calories: " + plan2.getTotalCalories());

            TextView name1 = findViewById(R.id.plan1Name);
            TextView carbon1 = findViewById(R.id.plan1CarbonPercentage);
            TextView protein1 = findViewById(R.id.plan1ProteinPercentage);
            TextView fat1 = findViewById(R.id.plan1FatPercentage);
            TextView totalCalories1 = findViewById(R.id.plan1DailyCalories);

            TextView name2 = findViewById(R.id.plan2Name);
            TextView carbon2 = findViewById(R.id.plan2CarbonPercentage);
            TextView protein2 = findViewById(R.id.plan2ProteinPercentage);
            TextView fat2 = findViewById(R.id.plan2FatPercentage);
            TextView totalCalories2 = findViewById(R.id.plan2DailyCalories);

            // Display comparison result

            name1.setText(plan1.getPlanName());
            carbon1.setText(String.format("%.1f %%", plan1.getCarbonPercent()));
            protein1.setText(String.format("%.1f %%", plan1.getProteinPercent()));
            fat1.setText(String.format("%.1f %%", plan1.getFatPercent()));
            totalCalories1.setText(String.valueOf(plan1.getTotalCalories()));

            name2.setText(plan2.getPlanName());
            carbon2.setText(String.format("%.1f %%", plan2.getCarbonPercent()));
            protein2.setText(String.format("%.1f %%", plan2.getProteinPercent()));
            fat2.setText(String.format("%.1f %%", plan2.getFatPercent()));
            totalCalories2.setText(String.valueOf(plan2.getTotalCalories()));

        }


        // Create buttons
        Button makeNewComparisonButton = findViewById(R.id.makeNewComparisonButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        // Set click listener for makeNewComparison button
        makeNewComparisonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparisonResultUI.this, CompareMealPlansUI_SelectPlans.class);
                startActivity(intent);
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparisonResultUI.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}