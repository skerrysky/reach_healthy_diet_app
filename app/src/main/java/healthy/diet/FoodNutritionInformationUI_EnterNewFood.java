package healthy.diet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FoodNutritionInformationUI_EnterNewFood extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nutrition_information_details_ui);
        myDb = new DatabaseHelper(this);

        // Get input from user
        EditText foodName = findViewById(R.id.foodNameID);
        EditText carbonPerServing = findViewById(R.id.carbonPerServingID);
        EditText proteinPerServing = findViewById(R.id.proteinPerServingID);
        EditText fatPerServing = findViewById(R.id.fatPerServingID);

        // Restrict carbonPerServing > 0
        InputFilter filterCarbonPerServing = new InputFilter() {
            final double minRange = 0.0;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    String inputStr = dest.subSequence(0, dstart).toString() + source.subSequence(start, end) + dest.subSequence(dend, dest.length());
                    double inputVal = Double.parseDouble(inputStr);
                    if (inputVal >= minRange) {
                        return null;
                    }
                } catch (NumberFormatException e) {

                }
                Toast.makeText(FoodNutritionInformationUI_EnterNewFood.this, "Error: Enter value larger than 0", Toast.LENGTH_LONG).show();
                return "";
            }
        };
        carbonPerServing.setFilters(new InputFilter[]{filterCarbonPerServing});

        // Restrict proteinPerServing > 0
        InputFilter filterProteinPerServing = new InputFilter() {
            final double minRange = 0.0;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    String inputStr = dest.subSequence(0, dstart).toString() + source.subSequence(start, end) + dest.subSequence(dend, dest.length());
                    double inputVal = Double.parseDouble(inputStr);
                    if (inputVal >= minRange) {
                        return null;
                    }
                } catch (NumberFormatException e) {

                }
                Toast.makeText(FoodNutritionInformationUI_EnterNewFood.this, "Error: Enter value larger than 0", Toast.LENGTH_LONG).show();
                return "";
            }
        };
        proteinPerServing.setFilters(new InputFilter[]{filterProteinPerServing});

        // Restrict fatPerServing > 0
        InputFilter filterFatPerServing = new InputFilter() {
            final double minRange = 0.0;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    String inputStr = dest.subSequence(0, dstart).toString() + source.subSequence(start, end) + dest.subSequence(dend, dest.length());
                    double inputVal = Double.parseDouble(inputStr);
                    if (inputVal >= minRange) {
                        return null;
                    }
                } catch (NumberFormatException e) {

                }
                Toast.makeText(FoodNutritionInformationUI_EnterNewFood.this, "Error: Enter value larger than 0", Toast.LENGTH_LONG).show();
                return "";
            }
        };
        fatPerServing.setFilters(new InputFilter[]{filterFatPerServing});


        Button saveButton = findViewById(R.id.saveButtonID);
        Button cancelButton = findViewById(R.id.cancelButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        // Set click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!(foodName.getText().toString().isEmpty() || carbonPerServing.getText().toString().isEmpty() || proteinPerServing.getText().toString().isEmpty() || fatPerServing.getText().toString().isEmpty() ))
                {
                    // Store the user inputs
                    String foodNameInput = foodName.getText().toString();
                    double carbonPerServingInput = Double.parseDouble(carbonPerServing.getText().toString());
                    double proteinPerServingInput = Double.parseDouble(proteinPerServing.getText().toString());
                    double fatPerServingInput = Double.parseDouble(fatPerServing.getText().toString());

                    // Create new food object
                    DataAndOperations.Food newFood = new DataAndOperations.Food(foodNameInput, carbonPerServingInput, proteinPerServingInput, fatPerServingInput);
                    // Save food to the database
                    long newFoodID = myDb.getOrCreateFoodId(newFood);

                    // Error handling for food insertion to the database
                    if (newFoodID != -1) {
                        Toast.makeText(FoodNutritionInformationUI_EnterNewFood.this, "Data inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FoodNutritionInformationUI_EnterNewFood.this, "Error inserting data", Toast.LENGTH_LONG).show();
                    }

                    finish();

                    // Return to main menu
                    Intent intent = new Intent(FoodNutritionInformationUI_EnterNewFood.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(FoodNutritionInformationUI_EnterNewFood.this, "Please fill in all the fields.", Toast.LENGTH_LONG).show();
                }

            }
        });

        // Set click listener for cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodNutritionInformationUI_EnterNewFood.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodNutritionInformationUI_EnterNewFood.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}