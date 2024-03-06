package healthy.diet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button expectedDailyCaloricIntakeButton = findViewById(R.id.expectedDailyCaloricIntakeID);
        Button foodNutritionButton = findViewById(R.id.foodNutritionButtonID);
        Button dailyMealPlansButton = findViewById(R.id.dailyMealPlansButtonID);
        Button compareMealPlansButton = findViewById(R.id.compareMealPlansButtonID);

        // Set click listener for viewCurrentJob button
        expectedDailyCaloricIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpectedDailyCaloricIntakeUI.class);
                startActivity(intent);
            }
        });

        // Set click listener for viewJobOffers button
        foodNutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodNutritionInformationUI.class); // Assume you have a JobOffersUI activity
                startActivity(intent);
            }
        });

        dailyMealPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyMealPlansUI.class); // Assume you have a JobOffersUI activity
                startActivity(intent);
            }
        });

        compareMealPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompareMealPlansUI.class); // Assume you have a JobOffersUI activity
                startActivity(intent);
            }
        });
    }
}