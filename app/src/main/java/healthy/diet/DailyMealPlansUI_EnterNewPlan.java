package healthy.diet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DailyMealPlansUI_EnterNewPlan extends AppCompatActivity {
    DatabaseHelper myDb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan_food_selection_ui);
        myDb = new DatabaseHelper(this);
        // Get expected caloric intake range
        DataAndOperations.ExpectedDailyCaloricIntake caloricRange = myDb.getExpectedDailyCaloricIntake();

        // Get input from user
        EditText planName = findViewById(R.id.planNameID);
        Button createPlanButton = findViewById(R.id.createPlanButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        // Read in the list of foods
        ArrayList<DataAndOperations.Food> allFoods = myDb.getAllFoods();

        // Define layout
        LinearLayout foodSelectionLayout = findViewById(R.id.foodSelectionLayout);

        // Create a map for EditTexts and Foods to trace the references
        Map<EditText, DataAndOperations.Food> editTextFoodMap = new HashMap<>();

        // Create UI components for each food item
        for (DataAndOperations.Food food : allFoods) {
            LinearLayout foodLayout = new LinearLayout(this);
            foodLayout.setOrientation(LinearLayout.HORIZONTAL);
            foodLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Create TextView for food name
            TextView foodNameTextView = new TextView(this);
            foodNameTextView.setText(food.getFoodName());
            foodNameTextView.setTextSize(14);
            // Set the layout parameters to match the button's size
            foodNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1
            ));


            // Create EditText for servings
            EditText servingsEditText = new EditText(this);
            servingsEditText.setHint("Servings");
            servingsEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            servingsEditText.setTextSize(14);
            servingsEditText.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1
            ));

            // Add the EditText-Food association to the map
            editTextFoodMap.put(servingsEditText, food);

            // Add TextView and EditText to the food layout
            foodLayout.addView(foodNameTextView);
            foodLayout.addView(servingsEditText);

            // Add food layout to the main layout
            foodSelectionLayout.addView(foodLayout);
        }

        // Set click listener for createPlanButton
        createPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DataAndOperations.Food> selectedFoods = new ArrayList<>();
                Map<DataAndOperations.Food, Integer> selectedFoodServings = new HashMap<>();

                // Loop through EditTexts to get selected foods and servings
                for (Map.Entry<EditText, DataAndOperations.Food> entry : editTextFoodMap.entrySet()) {
                    EditText servingsEditText = entry.getKey();
                    DataAndOperations.Food food = entry.getValue();

                    // Check if servings are entered
                    if (!servingsEditText.getText().toString().isEmpty()) {
                        int servings = Integer.parseInt(servingsEditText.getText().toString());
                        selectedFoodServings.put(food, servings);
                        selectedFoods.add(food);
                    }
                }
                if (!selectedFoods.isEmpty() && !planName.getText().toString().isEmpty()) {
                    // Store the user input
                    String planNameInput = planName.getText().toString();
                    // Create a new meal plan with the selected foods and servings
                    DataAndOperations.DailyMealPlan newMealPlan = new DataAndOperations.DailyMealPlan();
                    newMealPlan.setPlanName(planNameInput);
                    for (DataAndOperations.Food food : selectedFoods) {
                        int servings = selectedFoodServings.get(food);
                        newMealPlan.addFood(food, servings);
                    }

                    // Get minimum and maximum caloric intake
                    if (caloricRange != null) {
                        double totalCalories = newMealPlan.obtainTotalCalories();
                        double carbonPerc = newMealPlan.getCarbonPercent();
                        double proteinPerc = newMealPlan.getProteinPercent();
                        double fatPerc = newMealPlan.getFatPercent();
                        double minCaloricValue = caloricRange.getExpectedMinimumValue();
                        double maxCaloricValue = caloricRange.getExpectedMaximumValue();
                        // Check if the caloric intake is within the range
                        // Check if nutrition percent is within the healthy range
                        if (totalCalories >= minCaloricValue && totalCalories <= maxCaloricValue) {
                            if (carbonPerc >= 45 && carbonPerc <= 65
                                    && proteinPerc >= 10 && proteinPerc <= 35
                                    && fatPerc >= 20 && fatPerc <= 35) {
                                // Display the meal plan details
                                String message = "New Meal Plan:\n" +
                                        "Name: " + newMealPlan.getPlanName() + "\n" +
                                        "Total Calories: " + totalCalories + "\n" +
                                        "Carbon Percent: " + carbonPerc + "\n" +
                                        "Protein Percent: " + proteinPerc + "\n" +
                                        "Fat Percent: " + fatPerc;
                                showDetails("New Meal Plan", message);
                                // Save meal plan to the database
                                long newPlanID = myDb.createMealPlan(newMealPlan);
                                // Error handling for food insertion to the database
                                if (newPlanID != -1) {
                                    Toast.makeText(DailyMealPlansUI_EnterNewPlan.this, "Data inserted", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(DailyMealPlansUI_EnterNewPlan.this, "Error inserting data", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(DailyMealPlansUI_EnterNewPlan.this,
                                        "Error: Nutrition percent must be within the healthy range: carbon 45-65%, protein 10-35%, fat: 20-35%",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(DailyMealPlansUI_EnterNewPlan.this,
                                    "Error: Caloric intake must be within the range of " + minCaloricValue + " and " + maxCaloricValue,
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(DailyMealPlansUI_EnterNewPlan.this,
                                "Error: Please make sure to set the expected caloric intake first",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DailyMealPlansUI_EnterNewPlan.this, "Please enter plan name and enter servings for at least one food.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyMealPlansUI_EnterNewPlan.this, MainActivity.class);
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
