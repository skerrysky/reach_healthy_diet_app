package healthy.diet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Map;
import java.util.ArrayList;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Create the full application database
    public static final String DATABASE_NAME = "MealPlanCompare.db";

    // 1) Expected Daily Caloric Intake:
    public static final String TABLE_NAME_EXPECTED_CALORIC_INTAKE = "expected_caloric_intake_table";
    public static final String COLUMN_CALORIE_ID = "calorieId";
    public static final String COLUMN_EXPECTED_MINIMUM_VALUE = "expectedMinimumValue";
    public static final String COLUMN_EXPECTED_MAXIMUM_VALUE = "expectedMaximumValue";

    // 2) Food: including name, carbon, protein, fat grams per serving
    public static final String TABLE_NAME_FOODS = "foods_table";
    public static final String COLUMN_FOOD_ID = "foodId";
    public static final String COLUMN_FOOD_NAME = "foodName";
    public static final String COLUMN_CARBON_PER_SERVING = "carbonPerServing";
    public static final String COLUMN_PROTEIN_PER_SERVING = "proteinPerServing";
    public static final String COLUMN_FAT_PER_SERVING = "fatPerServing";

    // 3) Meal plan made up of food: including food and the count of its servings (max 10)
    // Meal plan - Food relation
    public static final String TABLE_NAME_MEAL_PLAN_FOODS = "meal_plan_foods_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEAL_PLAN_ID = "mealPlanId";
    public static final String COLUMN_MEAL_PLAN_FOOD_ID = "mealPlanFoodId";
    public static final String COLUMN_SERVINGS = "servings";

    // 4) Meal plan list: a list of meal plans for comparison, with computed total calories, carbon, protein, and fat percent for each plan
    public static final String TABLE_NAME_MEAL_PLANS = "meal_plans_table";
    public static final String COLUMN_PLAN_ID = "planId";
    public static final String COLUMN_PLAN_NAME = "mealPlanName";
    public static final String COLUMN_TOTAL_CALORIES = "totalCalories";
    public static final String COLUMN_CARBON_PERCENT = "carbonPercent";
    public static final String COLUMN_PROTEIN_PERCENT = "proteinPercent";
    public static final String COLUMN_FAT_PERCENT = "fatPercent";

    // Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Create food and meal plans tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create expected_caloric_intake_table
        String createExpectedCaloricIntakeTable = "CREATE TABLE " + TABLE_NAME_EXPECTED_CALORIC_INTAKE + " (" +
                COLUMN_CALORIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPECTED_MINIMUM_VALUE + " INTEGER NOT NULL, " +
                COLUMN_EXPECTED_MAXIMUM_VALUE + " INTEGER NOT NULL)";
        db.execSQL(createExpectedCaloricIntakeTable);

        // Create foods_table
        String createFoodsTable = "CREATE TABLE " + TABLE_NAME_FOODS + " (" +
                COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FOOD_NAME + " TEXT NOT NULL, " +
                COLUMN_CARBON_PER_SERVING + " REAL NOT NULL, " +
                COLUMN_PROTEIN_PER_SERVING + " REAL NOT NULL, " +
                COLUMN_FAT_PER_SERVING + " REAL NOT NULL)";
        db.execSQL(createFoodsTable);

        // Create meal_plan_foods_table
        String createMealPlanFoodsTable = "CREATE TABLE " + TABLE_NAME_MEAL_PLAN_FOODS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEAL_PLAN_ID + " INTEGER NOT NULL, " +
                COLUMN_MEAL_PLAN_FOOD_ID + " INTEGER NOT NULL, " +
                COLUMN_SERVINGS + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_MEAL_PLAN_ID + ") REFERENCES " + TABLE_NAME_MEAL_PLANS + "(" + COLUMN_PLAN_ID + "), " +
                "FOREIGN KEY (" + COLUMN_MEAL_PLAN_FOOD_ID + ") REFERENCES " + TABLE_NAME_FOODS + "(" + COLUMN_FOOD_ID + "))";
        db.execSQL(createMealPlanFoodsTable);

        // Create meal_plans_table
        String createMealPlansTable = "CREATE TABLE " + TABLE_NAME_MEAL_PLANS + " (" +
                COLUMN_PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAN_NAME + " TEXT NOT NULL, " +
                COLUMN_TOTAL_CALORIES + " REAL NOT NULL, " +
                COLUMN_CARBON_PERCENT + " REAL NOT NULL, " +
                COLUMN_PROTEIN_PERCENT + " REAL NOT NULL, " +
                COLUMN_FAT_PERCENT + " REAL NOT NULL)";
        db.execSQL(createMealPlansTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXPECTED_CALORIC_INTAKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEAL_PLAN_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEAL_PLANS);

        // Create new tables
        onCreate(db);
    }


    // Insert or update expected daily caloric intake
    public boolean insertOrUpdateExpectedCaloricIntake(double expectedMinimumValue, double expectedMaximumValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to store the inserted data
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPECTED_MINIMUM_VALUE, expectedMinimumValue);
        values.put(COLUMN_EXPECTED_MAXIMUM_VALUE, expectedMaximumValue);

        // Check if there is an existing record
        Cursor cursor = db.query(TABLE_NAME_EXPECTED_CALORIC_INTAKE,
                null,
                null,
                null,
                null,
                null,
                null);

        boolean success;

        if (cursor != null && cursor.moveToFirst()) {
            // Update the existing record
            int rowsChanged = db.update(TABLE_NAME_EXPECTED_CALORIC_INTAKE, values, null, null);
            success = rowsChanged > 0;
            cursor.close();
        } else {
            // Insert a new record
            long newId = db.insert(TABLE_NAME_EXPECTED_CALORIC_INTAKE, null, values);
            success = newId != -1;
            if (cursor != null) {
                cursor.close();
            }
        }

        // Close the database connection
        db.close();

        // Return the success status
        return success;
    }


    // Get or create a food entry and return its ID
    public long getOrCreateFoodId(DataAndOperations.Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if the food already exists in the food_table
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_FOOD_ID + " FROM " + TABLE_NAME_FOODS +
                " WHERE " + COLUMN_FOOD_NAME + " = ?", new String[]{food.getFoodName()});

        if (cursor.moveToFirst()) {
            // If the food exists, return its ID
            long foodId = cursor.getLong((int) cursor.getColumnIndex(COLUMN_FOOD_ID));
            cursor.close();
            return foodId;
        } else {
            // If the food doesn't exist, insert it and return the new ID
            ContentValues foodValues = new ContentValues();
            foodValues.put(COLUMN_FOOD_NAME, food.getFoodName());
            foodValues.put(COLUMN_CARBON_PER_SERVING, food.getCarbonPerServing());
            foodValues.put(COLUMN_PROTEIN_PER_SERVING, food.getProteinPerServing());
            foodValues.put(COLUMN_FAT_PER_SERVING, food.getFatPerServing());

            long newFoodId = db.insert(TABLE_NAME_FOODS, null, foodValues);
            cursor.close();
            return newFoodId;
        }
    }

    // Create a new meal plan and insert it to meal_plans_table
    public long createMealPlan(DataAndOperations.DailyMealPlan plan) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to store the inserted data
        ContentValues mealPlanValues = new ContentValues();
        mealPlanValues.put(COLUMN_PLAN_NAME, plan.getPlanName());
        mealPlanValues.put(COLUMN_TOTAL_CALORIES, plan.obtainTotalCalories());
        mealPlanValues.put(COLUMN_CARBON_PERCENT, plan.getCarbonPercent());
        mealPlanValues.put(COLUMN_PROTEIN_PERCENT, plan.getProteinPercent());
        mealPlanValues.put(COLUMN_FAT_PERCENT, plan.getFatPercent());

        // Insert the data into the Meal Plan table and retrieve the inserted ID
        long newPlanId = db.insert(TABLE_NAME_MEAL_PLANS, null, mealPlanValues);
        return newPlanId;
    }


    // Insert a meal plan with foods into meal_plan_foods_table
    public void insertPlanWithFoods(long mealPlanId, Map<DataAndOperations.Food, Integer> selectedFoodServings) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert associated foods into the meal_plan_foods_table
        for (Map.Entry<DataAndOperations.Food, Integer> entry : selectedFoodServings.entrySet()) {
            DataAndOperations.Food food = entry.getKey();
            int servings = entry.getValue();

            ContentValues mealPlanFoodValues = new ContentValues();
            mealPlanFoodValues.put(COLUMN_MEAL_PLAN_ID, mealPlanId);
            mealPlanFoodValues.put(COLUMN_MEAL_PLAN_FOOD_ID, getOrCreateFoodId(food));
            mealPlanFoodValues.put(COLUMN_SERVINGS, servings);

            // Insert the meal plan food data into the Meal Plan Foods table
            db.insert(TABLE_NAME_MEAL_PLAN_FOODS, null, mealPlanFoodValues);
        }

        // Close the database connection
        db.close();
    }


    // Get expected daily caloric intake
    public DataAndOperations.ExpectedDailyCaloricIntake getExpectedDailyCaloricIntake() {
        SQLiteDatabase db = this.getReadableDatabase();
        DataAndOperations.ExpectedDailyCaloricIntake caloricIntake = null;

        // Define the columns to be retrieved
        String[] projection = {
                COLUMN_CALORIE_ID,
                COLUMN_EXPECTED_MINIMUM_VALUE,
                COLUMN_EXPECTED_MAXIMUM_VALUE
        };

        // Query the table to get the expected caloric intake
        Cursor cursor = db.query(
                TABLE_NAME_EXPECTED_CALORIC_INTAKE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create ExpectedDailyCaloricIntake object
            double expectedMinimumValue = cursor.getDouble((int) cursor.getColumnIndex(COLUMN_EXPECTED_MINIMUM_VALUE));
            double expectedMaximumValue = cursor.getDouble((int) cursor.getColumnIndex(COLUMN_EXPECTED_MAXIMUM_VALUE));

            caloricIntake = new DataAndOperations.ExpectedDailyCaloricIntake();
            caloricIntake.setExpectedMinimumValue(expectedMinimumValue);
            caloricIntake.setExpectedMaximumValue(expectedMaximumValue);

            cursor.close();
        }

        // Close the database connection
        db.close();

        return caloricIntake;
    }


    // Get all meal plans from meal_plans_table
    public ArrayList<DataAndOperations.DailyMealPlan> getAllMealPlans() {
        ArrayList<DataAndOperations.DailyMealPlan> mealPlans = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to be retrieved
        String[] columns = {
                COLUMN_PLAN_NAME,
                COLUMN_TOTAL_CALORIES,
                COLUMN_CARBON_PERCENT,
                COLUMN_PROTEIN_PERCENT,
                COLUMN_FAT_PERCENT
        };

        // Query the meal plans table
        Cursor cursor = db.query(TABLE_NAME_MEAL_PLANS, columns, null, null, null, null, null);

        // Check if there are any rows
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a DailyMealPlan object and set the values
                DataAndOperations.DailyMealPlan mealPlan = new DataAndOperations.DailyMealPlan();
                mealPlan.setPlanName(cursor.getString((int) cursor.getColumnIndex(COLUMN_PLAN_NAME)));
                mealPlan.setTotalCalories(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_TOTAL_CALORIES)));
                mealPlan.setCarbonPercent(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_CARBON_PERCENT)));
                mealPlan.setProteinPercent(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_PROTEIN_PERCENT)));
                mealPlan.setFatPercent(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_FAT_PERCENT)));

                mealPlans.add(mealPlan);
                // Debug statements
                Log.d("DatabaseDebug", "Added Meal Plan: " + mealPlan.getPlanName());
                Log.d("DatabaseDebug", "Total: " + mealPlan.getTotalCalories());
                Log.d("DatabaseDebug", "Carbon: " + mealPlan.getCarbonPercent());
                Log.d("DatabaseDebug", "Protein: " + mealPlan.getProteinPercent());
                Log.d("DatabaseDebug", "Fat: " + mealPlan.getFatPercent());


            } while (cursor.moveToNext());
            // Close the cursor and database
            cursor.close();
        }

        db.close();

        // Debug statement
        Log.d("DatabaseDebug", "Total Meal Plans Retrieved: " + mealPlans.size());


        return mealPlans;
    }


    // Get all foods from foods_table
    public ArrayList<DataAndOperations.Food> getAllFoods() {
        ArrayList<DataAndOperations.Food> foodsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {
                COLUMN_FOOD_NAME,
                COLUMN_CARBON_PER_SERVING,
                COLUMN_PROTEIN_PER_SERVING,
                COLUMN_FAT_PER_SERVING
        };

        // Query the foods_table
        Cursor cursor = db.query(TABLE_NAME_FOODS, columns, null, null, null, null, null);

        // Iterate through the cursor and add each food to the list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DataAndOperations.Food food = new DataAndOperations.Food();
                food.setFoodName(cursor.getString((int) cursor.getColumnIndex(COLUMN_FOOD_NAME)));
                food.setCarbonPerServing(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_CARBON_PER_SERVING)));
                food.setProteinPerServing(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_PROTEIN_PER_SERVING)));
                food.setFatPerServing(cursor.getDouble((int) cursor.getColumnIndex(COLUMN_FAT_PER_SERVING)));

                foodsList.add(food);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database connection
        db.close();

        return foodsList;
    }

}
