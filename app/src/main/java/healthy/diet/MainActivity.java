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

        expectedDailyCaloricIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpectedDailyCaloricIntakeUI.class);
                startActivity(intent);
            }
        });

        foodNutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodNutritionInformationUI.class);
                startActivity(intent);
            }
        });

        dailyMealPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyMealPlansUI.class);
                startActivity(intent);
            }
        });

        compareMealPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompareMealPlansUI.class);
                startActivity(intent);
            }
        });
    }
}