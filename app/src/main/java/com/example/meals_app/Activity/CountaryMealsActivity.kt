package com.example.meals_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meals_app.Activity.MealActivity
import com.example.meals_app.Adapter.MealsAdapter
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealList
import com.example.meals_app.Data.categoryMeal
import com.example.meals_app.Retrofit.Retrofit_Helper
import com.example.meals_app.viewModel.CountryMealsViewModel
import com.example.meals_app.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountaryMealsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MealsAdapter
    private lateinit var countaryMVVM :CountryMealsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countary_meals)

        recyclerView = findViewById(R.id.RV_MEALS)
        progressBar = findViewById(R.id.PROGRESS_BAR_)
       // mealCard = findViewById(R.layout.meal_item_card)

        countaryMVVM =  ViewModelProvider(this)[CountryMealsViewModel::class.java]

        adapter = MealsAdapter { mealId ->
            countaryMVVM.fetchMealDetails(mealId)

        }
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        // Retrieve country name from intent
        val countryName = intent.getStringExtra("COUNTRY_NAME")

        // Fetch meals by country
        if (countryName != null) {
            countaryMVVM.getMealsByCountry(countryName)
            observeCountryMeal()
        }
        observeMealDetails()
    }
    private fun observeCountryMeal(){
        countaryMVVM.CountryLiveData.observe(this,{Meals->
            adapter.setMeals(Meals)
            progressBar.visibility  = View.GONE
        })
    }


    private fun observeMealDetails() {
        countaryMVVM.CountaryMealDetails.observe(this, { meal ->
            meal?.let {
                passMealDetailsToMealActivity(it)
            }
        })
    }


    // Function to pass meal details to MealActivity
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

    private fun getIngredientsList(meal: Meal): ArrayList<String> {
        val ingredients = ArrayList<String>()

        meal.strIngredient1?.let { ingredients.add(it) }
        meal.strIngredient2?.let { ingredients.add(it) }
        meal.strIngredient3?.let { ingredients.add(it) }
        meal.strIngredient4?.let { ingredients.add(it) }
        meal.strIngredient5?.let { ingredients.add(it) }
        meal.strIngredient6?.let { ingredients.add(it) }
        meal.strIngredient7?.let { ingredients.add(it) }
        meal.strIngredient8?.let { ingredients.add(it) }
        meal.strIngredient9?.let { ingredients.add(it) }
        meal.strIngredient10?.let { ingredients.add(it) }
        meal.strIngredient11?.let { ingredients.add(it) }
        meal.strIngredient12?.let { ingredients.add(it) }
        meal.strIngredient13?.let { ingredients.add(it) }
        meal.strIngredient14?.let { ingredients.add(it) }
        meal.strIngredient15?.let { ingredients.add(it) }
        return ingredients
    }
}
