# Android Project: Healthy Diet App

## Links to Codes and Demos

1. I used Android Studio to create this app.
The full project’s link can be found in my GitHub: https://github.com/skerrysky/reach_healthy_diet_app
My backend .java files can be found here: https://github.com/skerrysky/reach_healthy_diet_app/tree/master/app/src/main/java/healthy/diet
My frontend .xml files can be found here: https://github.com/skerrysky/reach_healthy_diet_app/tree/master/app/src/main/res/layout


2. I also recorded two demos to show the app’s functionalities. They can be found in my YouTube account.
The short one quickly demonstrates its core features: https://youtu.be/bak720yxyrY
The long one is a full walkthrough of all of the app’s functionalities starting from preferred calories and food information entry: https://youtu.be/bPtMFBgaPUo 

## Overview


The primary objective of the Healthy Diet app is to empower users to manage their diet effectively, promoting a healthy lifestyle. The app provides a dynamic platform to input personal preferences and food nutrition information. Users would be able to create and compare daily meal plans tailored to individual preferences and nutritional goals.


## Background


It is challenging to maintain a healthy diet with balanced nutrition in today’s fast-paced world. The abundance of food choices and nutritional information can be overwhelming. How to keep the total daily caloric intake within a healthy range? How to diversify the food choices to ensure sufficient intake of different nutrients, such as carbon, protein, and fat? Tackling such challenges in my own life, I decided to build the Healthy Diet app to facilitate diet management. It serves users who are conscious of their nutritional intake, providing an easy tool for planning, tracking, analyzing, and adapting their daily meals.


## Key Features


1. Main Menu


Users can select from 4 options from the main menu: a) Expected Daily Caloric Intake; b) Food Nutrition; c) Daily Meal Plans; d) Compare Daily Meal Plans. I will explain what users can do after selecting each option one by one below. 


2. Expected Daily Caloric Intake


This main menu option allows users to enter their preferred daily caloric intake. They will input expected minimum and maximum values, which must fall in the range from 800 to 3500 calories. When creating a new meal plan, its total calories must be within the range of entered min and max values. Users can view or edit the values if they have a preference change. 


3. Food Nutrition 


This main menu option allows users to enter food details, including name, carbohydrate, protein, and fat content per serving (in grams), e.g., Apple, carbon 25g, protein 0.5g, fat 0.3g per serving. Users can only add food that is not already added. Users can view the full list of all added food information as well.


4. Daily Meal Plans


This main menu option allows users to create and view daily meal plans. To create a new plan, users need to enter a plan name, select from the added foods, input the number of servings for each food. The app will automatically calculate and display the selected food's calorie count, and the percentage contributions of carbon, protein, fat respectively (e.g., total cal 2300, carbon 50%, protein 25%, fat 25%). 


To ensure the plan represents a healthy diet, the app checks if the total calories fall within the preferred range and if macronutrient ratios adhere to acceptable ranges: Carbon: 45% to 65% of total calories; Protein: 10% to 35% of total calories; Fat: 20% to 35% of total calories. Only plans that pass the check can be created. Users can view the full list of all added meal plans as well.


5. Compare Daily Meal Plans


This main menu option allows users to select and compare two stored meal plans, displaying their calories and macronutrient percentages side by side in a table. Users can make as many comparisons of two selected plans as they want. 



## Note


All data is securely stored locally on the user's device. All the food nutrition information is only for demonstration purposes based on rough estimations. For more accurate details, please consult the FoodData Central database provided by USDA (United States Department of Agriculture). https://fdc.nal.usda.gov/fdc-app.html#/


## Challenges: 


- Challenges that I Solved:


1. Local Data Storage and Retrieval:


The most interesting part of this project was applying my database knowledge such as SQLite to build local tables for data storage and retrieval. The logic was easy: I needed 2 tables to store and retrieve food and meal plan details respectively. However, to achieve the goal, I must correctly build tables and write methods such as createMealPlan() and getAllMealPlans() in DatabaseHelper.java, so that they can be properly applied in corresponding java files. It is challenging yet exciting to figure out how to make different backend classes and methods “talk” with one another. Understanding their collective function and interplay is the most alluring part of backend engineering.


2. Input Validation and Error Handling:


Another exciting challenge was validating user inputs to achieve the goal of maintaining a healthy diet. I must make sure the entered expected daily calorie range and the meal plan’s nutrition distribution fall within reasonable ranges. To address this, I applied lots of error handling (Toast) in classes such as DailyMealPlan_EnterNewPlan.java to give users appropriate feedback. This experience greatly improved my ability to handle complex functionalities and create a user-friendly interface.


- Challenges to be Solved Next:


1. Optimized Algorithm for Meal Planning:


The app in its current stage involves basic functionalities, and I could add more advanced features next. For instance, the app could automatically recommend a balanced meal plan based on a variety of food users add. To realize this, I will need to figure out how to develop more complex algorithms to optimize meal planning. 


2. Integration with External APIs to Use AI Techniques


Another possibility is to leverage the power of AI and machine learning to make optimal recommendations through integration with external APIs. This may allow for incorporating other nutritional concerns and personal preferences into the calculation. To achieve this, I will need to explore how to integrate APIs into my app's backend, allowing seamless communication between my app and external services.
