package healthy.diet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class FoodNutritionInformationUI extends AppCompatActivity {

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nutrition_information_ui);

        Button viewFoodNutritionInformationButton = findViewById(R.id.viewFoodNutritionInformationButtonID);
        Button enterFoodNutritionInformationButton = findViewById(R.id.enterFoodNutritionInformationButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        myDb = new DatabaseHelper(this);

        // Set click listener for viewFoodNutritionInformation button
        viewFoodNutritionInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DataAndOperations.Food> foodNutritionInformation = myDb.getAllFoods();

                if (foodNutritionInformation.isEmpty()) {
                    showDetails("Error", "No foods available.");
                } else {
                    StringBuilder foodDetails = new StringBuilder();
                    for (DataAndOperations.Food food : foodNutritionInformation) {
                        foodDetails.append("Food Name: ").append(food.getFoodName()).append("\n");
                        foodDetails.append("Carbon per Serving: ").append(food.getCarbonPerServing()).append("\n");
                        foodDetails.append("Protein per Serving: ").append(food.getProteinPerServing()).append("\n");
                        foodDetails.append("Fat per Serving: ").append(food.getFatPerServing()).append("\n\n");
                    }

                    showDetails("Food Nutrition Information", foodDetails.toString());
                }
            }
        });

        // Set click listener for enterFoodNutritionInformation button
        enterFoodNutritionInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodNutritionInformationUI.this, FoodNutritionInformationUI_EnterNewFood.class);
                startActivity(intent);
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodNutritionInformationUI.this, MainActivity.class);
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