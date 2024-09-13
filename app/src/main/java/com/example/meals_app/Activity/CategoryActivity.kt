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
import com.example.meals_app.Data.MealList
import com.example.meals_app.R
import com.example.meals_app.Retrofit.Retrofit_Helper
import com.example.meals_app.databinding.ActivityCategoryBinding
import com.example.meals_app.viewModel.CategoryMealsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        categoryMealsViewModel.categoryMealLiveData.observe(this, Observer { meal ->
            categoryMealsAdapter.setMeal(meal)
            cat_meals.text =categoryMealsAdapter.getItemCount().toString()  // asign number of items
        })
        var cate_name = intent.getStringExtra("Category_name") // cat_name
        categoryMealsViewModel.getMealByCatName(cate_name!!) // take category Name and put into meal in order call api

        //when meal is clicked it take its id and send to Navigate Meals
        categoryMealsAdapter.onMealClick = { meal ->
            NavigateMealDetails(meal.idMeal)
        }

    }

    // Take data and goes to random MEAL activity
    private fun NavigateMealDetails(idMeal: String) {
        val api = Retrofit_Helper.api
        api.getMealByID(idMeal).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.get(0)
                if (meal != null){
                    val intent = Intent(this@CategoryActivity, MealActivity::class.java).apply {
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
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
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
            //layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryMealsAdapter

        }

    }
}
