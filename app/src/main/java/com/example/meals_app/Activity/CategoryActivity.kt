package com.example.meals_app.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals_app.Adapter.Category.CategoryMealsAdapter
import com.example.meals_app.Data.Meal
import com.example.meals_app.R
import com.example.meals_app.databinding.ActivityCategoryBinding
import com.example.meals_app.viewModel.CategoryMealsViewModel

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    lateinit var cat_meals :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclView()
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]


        var category_name = intent.getStringExtra("Category_name") // cat_name

        categoryMealsViewModel.getMealByCatName(category_name!!) // take category Name and put into meal in order call api

      //observer
        categoryMealsViewModel.categoryMealLiveData.observe(this, Observer { meal ->
            categoryMealsAdapter.setMeal(meal)
            cat_meals.text =categoryMealsAdapter.getItemCount().toString()  // asign number of items
        })


        //when meal is clicked it take its id and send to Navigate Meals
        categoryMealsAdapter.onMealClick = { meal -> // take object of meal and then construct from it id
            categoryMealsViewModel.fetchMealById(meal.idMeal)
        }
      observeMealDetails() //observe Meal and Navigate to Meal Activity
    }

    private fun observeMealDetails() {
        categoryMealsViewModel._mealDetails.observe(this,{meal->
            if (meal != null) {
                passMealDetailsToMealActivity(meal)
            }
        })
    }

    private fun passMealDetailsToMealActivity(meal: Meal) {
        val intent = Intent(this, MealActivity::class.java).apply {
            putExtra("meal_id", meal.idMeal)
            putExtra("meal_name", meal.strMeal)
            putExtra("meal_image", meal.strMealThumb)
            putExtra("meal_instructions", meal.strInstructions)
            putExtra("category", meal.strCategory)
            putExtra("Area", meal.strArea)
            putExtra("meal_ingredients", getIngredientsList(meal))
            putExtra("youtube", meal.strYoutube)
        }
        startActivity(intent)
    }


    // list of incredients
    private fun getIngredientsList(meal: Meal): ArrayList<String> {
        return arrayListOf(
            meal.strIngredient1,
            meal.strIngredient2,
            meal.strIngredient3,
            meal.strIngredient4,
            meal.strIngredient5,
            meal.strIngredient6
        ).filterNotNull() as ArrayList<String>
    }

    // recycle view inizilize of categoryRV
    private fun prepareRecyclView() {
        cat_meals = findViewById(R.id.tv_category_sum)
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvCategoryMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter

        }

    }
}
