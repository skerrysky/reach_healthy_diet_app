package healthy.diet;
import android.content.Context;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DataAndOperations implements Serializable  {

    // ExpectedDailyCaloricIntake class as an inner class
    public static class ExpectedDailyCaloricIntake implements Serializable {

        public double expectedMinimumValue;
        public double expectedMaximumValue;

        // Constructor
        public ExpectedDailyCaloricIntake() {
        }

        // Getters and Setters
        public double getExpectedMinimumValue() {
            return expectedMinimumValue;
        }

        public void setExpectedMinimumValue(double expectedMinimumValue) {
            this.expectedMinimumValue = expectedMinimumValue;
        }

        public double getExpectedMaximumValue() {
            return expectedMaximumValue;
        }

        public void setExpectedMaximumValue(double expectedMaximumValue) {
            this.expectedMaximumValue = expectedMaximumValue;
        }
    }

    // Food class as an inner class
    public static class Food implements Serializable {

        public String foodName;
        public double carbonPerServing;
        public double proteinPerServing;
        public double fatPerServing;

        // Constructor
        public Food() {
        }

        public Food(String foodName, double carbonPerServing, double proteinPerServing, double fatPerServing) {
            this.foodName = foodName;
            this.carbonPerServing = carbonPerServing;
            this.proteinPerServing = proteinPerServing;
            this.fatPerServing = fatPerServing;
        }

        // Getters and Setters
        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public double getCarbonPerServing() {
            return carbonPerServing;
        }

        public void setCarbonPerServing(double carbonPerServing) {
            this.carbonPerServing = carbonPerServing;
        }

        public double getProteinPerServing() {
            return proteinPerServing;
        }

        public void setProteinPerServing(double proteinPerServing) {
            this.proteinPerServing = proteinPerServing;
        }

        public double getFatPerServing() {
            return fatPerServing;
        }

        public void setFatPerServing(double fatPerServing) {
            this.fatPerServing = fatPerServing;
        }
    }

    // DailyMealPlan class as an inner class
    public static class DailyMealPlan implements Serializable {

        private static final int MAX_FOOD_TYPES = 10;
        public Map<Food, Integer> selectedFoodServings;
        public String planName;
        public double totalCalories;
        public double carbonPercent;
        public double proteinPercent;
        public double fatPercent;

        // Constructor
        public DailyMealPlan() {
            selectedFoodServings = new HashMap<>();
            totalCalories = 0.0;
            carbonPercent = 0.0;
            proteinPercent = 0.0;
            fatPercent = 0.0;
        }

        // Add a selected food and its servings to the plan
        public void addFood(Food food, int servings) {
            if (selectedFoodServings.size() < MAX_FOOD_TYPES) {
                selectedFoodServings.put(food, servings);
                updateValues();
            } else {
                // Throw an exception if the number of food types in one plan exceeds 10
                throw new IllegalStateException("You can add at most 10 types of food in one meal plan.");
            }
        }

        // Update values directly after adding a new food
        private void updateValues() {
            // This is executed after adding the new food to the plan
            totalCalories = obtainTotalCalories(); // updated total
            double newCarbon = getCarbonCalories();
            double newProtein = getProteinCalories();
            double newFat = getFatCalories();
            if (totalCalories > 0) {
                carbonPercent = Math.round(newCarbon / totalCalories * 100.0); // Round to 1 decimal
                proteinPercent = Math.round(newProtein / totalCalories * 100.0); // Round to 1 decimal
                fatPercent = Math.round(newFat / totalCalories * 100.0); // Round to 1 decimal
            } else {
                carbonPercent = 0.0;
                proteinPercent = 0.0;
                fatPercent = 0.0;
            }
        }

        // Get the selected food and its servings
        public Map<Food, Integer> getSelectedFoodServings() {
            return selectedFoodServings;
        }

        // Get total calories
        public double obtainTotalCalories() {
            return DataAndOperations.calculateTotalCalories(this);
        }

        public double getTotalCalories() {
            return totalCalories;
        }

        // Get carbon percent
        public double getCarbonCalories() {
            return DataAndOperations.calculateCarbonCalories(this);
        }

        // Get protein percent
        public double getProteinCalories() {
            return DataAndOperations.calculateProteinCalories(this);
        }

        // Get fat percent
        public double getFatCalories() {
            return DataAndOperations.calculateFatCalories(this);
        }

        // Get mealPlanId
        public String getPlanName() {
            return planName;
        }

        public double getCarbonPercent() {
            return this.carbonPercent;
        }

        public double getProteinPercent() {
            return this.proteinPercent;
        }

        public double getFatPercent() {
            return this.fatPercent;
        }

        // Setters
        public void setTotalCalories(double totalCalories) {
            this.totalCalories = totalCalories;
        }

        public void setCarbonPercent(double carbonPercent) {
            this.carbonPercent = carbonPercent;
        }

        public void setProteinPercent(double proteinPercent) {
            this.proteinPercent = proteinPercent;
        }

        public void setFatPercent(double fatPercent) {
            this.fatPercent = fatPercent;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }



    }

    // DataAndOperations class methods and attributes
    public ExpectedDailyCaloricIntake calorieRange;
    public static ArrayList<DailyMealPlan> dailyMealPlans;
    public static ArrayList<DailyMealPlan> selectedPlans;

    // Default constructor
    public DataAndOperations(Context context) {
        // Initialize default values if needed
        this.dailyMealPlans = new ArrayList<>();
    }

    public DataAndOperations(ArrayList<DailyMealPlan> dailyMealPlans) {
        this.dailyMealPlans = dailyMealPlans;
    }

    // Calculate total calories for each meal plan
    public static double calculateTotalCalories(DailyMealPlan mealPlan) {

        double totalCalories = 0.0;
        Map<Food, Integer> selectedFoodServings = mealPlan.getSelectedFoodServings();
        for (Map.Entry<Food, Integer> entry : selectedFoodServings.entrySet()) {
            Food food = entry.getKey();
            int servings = entry.getValue();
            // Calculate calories based on the fixed formula
            double carbonCal = food.getCarbonPerServing() * servings * 4; // 1 g carbon = 4 kcal
            double proteinCal= food.getProteinPerServing() * servings * 4; // 1 g protein = 4 kcal
            double fatCal = food.getFatPerServing() * servings * 9; // 1 g fat = 9 kcal
            // Add each food's calories to the total
            totalCalories += carbonCal + proteinCal + fatCal;
        }
        return Math.round(totalCalories * 10.0) / 10.0;
    }

    // Calculate carbon calories for each meal plan
    public static double calculateCarbonCalories(DailyMealPlan mealPlan) {
        double carbonCalories = 0.0;
        Map<Food, Integer> selectedFoodServings = mealPlan.getSelectedFoodServings();
        for (Map.Entry<Food, Integer> entry : selectedFoodServings.entrySet()) {
            Food food = entry.getKey();
            int servings = entry.getValue();
            // Calculate calories based on the fixed formula
            double carbonCal = food.getCarbonPerServing() * servings * 4; // 1 g carbon = 4 kcal
            carbonCalories += carbonCal;
        }
        return carbonCalories;
    }

    // Calculate protein calories for each meal plan
    public static double calculateProteinCalories(DailyMealPlan mealPlan) {
        double proteinCalories = 0.0;
        Map<Food, Integer> selectedFoodServings = mealPlan.getSelectedFoodServings();
        for (Map.Entry<Food, Integer> entry : selectedFoodServings.entrySet()) {
            Food food = entry.getKey();
            int servings = entry.getValue();
            // Calculate calories based on the fixed formula
            double proteinCal = food.getProteinPerServing() * servings * 4; // 1 g carbon = 4 kcal
            proteinCalories += proteinCal;
        }
        return proteinCalories;
    }

    // Calculate fat calories for each meal plan
    public static double calculateFatCalories(DailyMealPlan mealPlan) {
        double fatCalories = 0.0;
        Map<Food, Integer> selectedFoodServings = mealPlan.getSelectedFoodServings();
        for (Map.Entry<Food, Integer> entry : selectedFoodServings.entrySet()) {
            Food food = entry.getKey();
            int servings = entry.getValue();
            // Calculate calories based on the fixed formula
            double fatCal = food.getFatPerServing() * servings * 9; // 1 g carbon = 4 kcal
            fatCalories += fatCal;
        }
        return fatCalories;
    }


    public static ExpectedDailyCaloricIntake buildExpectedDailyCaloricIntake(int expectedMinimumValue, int expectedMaximumValue) {
        ExpectedDailyCaloricIntake range = new ExpectedDailyCaloricIntake();
        range.expectedMinimumValue = expectedMinimumValue;
        range.expectedMaximumValue = expectedMaximumValue;
        return range;
    }


    // Return ranked meal plan list (ranked by total calories in ascending order)
    public static ArrayList<DailyMealPlan> getRankedMealPlanList(ArrayList<DataAndOperations.DailyMealPlan> PlanList) {

        // Return a ranked meal plan list based on the dailyMealPlans
        PlanList.sort(new Comparator<DailyMealPlan>() {
            public int compare(DailyMealPlan plan1, DailyMealPlan plan2) {
                double cal1 = plan1.getTotalCalories();
                double cal2 = plan2.getTotalCalories();
                // Sort in ascending order
                return Double.compare(cal1, cal2);
            }
        });

        return PlanList;
    }

}
