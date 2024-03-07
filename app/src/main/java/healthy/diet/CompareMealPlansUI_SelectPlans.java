package healthy.diet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompareMealPlansUI_SelectPlans extends AppCompatActivity {
    DatabaseHelper myDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_selection_ui);
        myDb = new DatabaseHelper(this);

        // Read in the list of meal plans
        ArrayList<DataAndOperations.DailyMealPlan> mealPlans = myDb.getAllMealPlans();

        // Get ranked meal plan list
        ArrayList<DataAndOperations.DailyMealPlan> rankedMealPlanList = DataAndOperations.getRankedMealPlanList(mealPlans);

        // Define checkbox layout
        LinearLayout checkBoxLayout = findViewById(R.id.checkBoxLayout);

        // Create a map for checkboxes to trace the references
        Map<CheckBox, DataAndOperations.DailyMealPlan> checkBoxPlanMap = new HashMap<>();

        // Create checkboxes for each meal plan in the ranked plan list
        for (DataAndOperations.DailyMealPlan plan: rankedMealPlanList) {
            CheckBox newBox = new CheckBox(CompareMealPlansUI_SelectPlans.this);
            // Set text for newBox: add in total calories to ensure ranking works
            newBox.setText("Meal Plan Name: " + plan.getPlanName() + "\nTotal Calories: " + plan.getTotalCalories());
            // Add the checkBox-Plan association to the map
            checkBoxPlanMap.put(newBox, plan);
            // Add checkbox to the layout
            checkBoxLayout.addView(newBox);
        }


        Button makeComparisonButton = findViewById(R.id.makeComparisonButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);


        // Set click listener for makeComparison button
        makeComparisonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] checkedCount = {0};
                final int maxChecked = 2;
                ArrayList<DataAndOperations.DailyMealPlan> selectedPlans = new ArrayList<>();

                // Loop through click events for handle checkboxes and add selected plans to the pool for comparison
                for (int i = 0; i < checkBoxLayout.getChildCount(); i++) {
                    // See if the child view is a checkbox
                    if (checkBoxLayout.getChildAt(i) instanceof CheckBox) {
                        CheckBox newBox = (CheckBox) checkBoxLayout.getChildAt(i);
                        // Set change listener for newBox

                        if (newBox.isChecked())
                        { // If checked

                            checkedCount[0]++;
                            DataAndOperations.DailyMealPlan newSelection = checkBoxPlanMap.get(newBox);

                            // Add the checked plan to the selectedPlan list
                            selectedPlans.add(newSelection);
                        }
                    }
                }

                if (selectedPlans.size() == 2) { // Only when selected 2 plans can uses make comparison
                    Intent intent = new Intent(CompareMealPlansUI_SelectPlans.this, ComparisonResultUI.class);
                    intent.putExtra("something", selectedPlans);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CompareMealPlansUI_SelectPlans.this, "Please make sure you select 2 plans", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CompareMealPlansUI_SelectPlans.this, CompareMealPlansUI_SelectPlans.class);
                    startActivity(intent);
                }

            }
        });



        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompareMealPlansUI_SelectPlans.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
    public void showDetails(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
