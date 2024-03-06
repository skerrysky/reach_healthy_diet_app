package healthy.diet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompareMealPlansUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_meal_plans_ui);

        Button startMealPlanComparisonButton = findViewById(R.id.startMealPlanComparisonButtonID);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButtonID);

        DataAndOperations dataAndOperations = (DataAndOperations) getIntent().getSerializableExtra("dataAndOperations");

        // Set click listener for startMealPlanComparison button
        startMealPlanComparisonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompareMealPlansUI.this, CompareMealPlansUI_SelectPlans.class);
                startActivity(intent);
            }
        });

        // Set click listener for returnToMenu button
        returnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompareMealPlansUI.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}