package com.example.meals_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meals_app.Activity.MealActivity
import com.example.meals_app.Adapter.MealsAdapter
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealList
import com.example.meals_app.Data.categoryMeal
import com.example.meals_app.Retrofit.Retrofit_Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountaryMealsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countary_meals)

        recyclerView = findViewById(R.id.RV_MEALS)
        progressBar = findViewById(R.id.PROGRESS_BAR_)

        adapter = MealsAdapter { mealId ->
            fetchMealDetails(mealId)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        // Retrieve country name from intent
        val countryName = intent.getStringExtra("COUNTRY_NAME") ?: return

        // Fetch meals by country
        fetchMealsByCountry(countryName)
    }

    private fun fetchMealsByCountry(country: String) {
        Retrofit_Helper.api.getMealsByCountary(country).enqueue(object : Callback<categoryMeal> {
            override fun onResponse(call: Call<categoryMeal>, response: Response<categoryMeal>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    adapter.setMeals(meals)
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<categoryMeal>, t: Throwable) {
                // Handle error
            }
        })
    }
    private fun fetchMealDetails(mealId: String) {
        Retrofit_Helper.api.getMealByID(mealId).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    meal?.let {
                        val intent = Intent(this@CountaryMealsActivity, MealActivity::class.java).apply {
                            putExtra("meal_id", it.idMeal)
                            putExtra("meal_name", it.strMeal)
                            putExtra("meal_image", it.strMealThumb)
                            putExtra("meal_instructions", it.strInstructions)
                            putExtra("category", it.strCategory)
                            putExtra("Area", it.strArea)
                            putExtra("meal_ingredients", getIngredientsList(it))
                            putExtra("youtube", it.strYoutube)
                        }
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun getIngredientsList(meal: Meal): ArrayList<String> {
        val ingredients = ArrayList<String>()
        meal.strIngredient1?.let { ingredients.add(it) }
        meal.strIngredient2?.let { ingredients.add(it) }
        meal.strIngredient3?.let { ingredients.add(it) }
        meal.strIngredient4?.let { ingredients.add(it) }
        meal.strIngredient5?.let { ingredients.add(it) }
        meal.strIngredient6?.let { ingredients.add(it) }
        return ingredients
    }
}
