package com.example.meals_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealList
import com.example.meals_app.Data.Mealcat
import com.example.meals_app.Data.categoryMeal
import com.example.meals_app.Retrofit.Retrofit_Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {
    val categoryMealLiveData = MutableLiveData<List<Mealcat>>()

    private val _mealDetails = MutableLiveData<Meal?>()
    val mealDetails: LiveData<Meal?> get() = _mealDetails


    // fetch meal by api and the put the value inside live data
    fun fetchMealById(mealId: String) {
        Retrofit_Helper.api.getMealByID(mealId).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let { mealList ->
                    val meal = mealList?.meals?.firstOrNull()
                    if (meal != null) {
                        // Update LiveData with the fetched meal details
                        _mealDetails.postValue(meal)
                    }
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // Log the error message if the API call fails
                Log.e("CategoryMealsViewModel", "Error fetching meal details", t)
            }
        })
    }



    fun getMealByCatName(catName:String){
        Retrofit_Helper.api.getMealsByCategory(catName).enqueue(object :Callback<categoryMeal>{
            override fun onResponse(call: Call<categoryMeal>, response: Response<categoryMeal>) {
                    response.body()?.let { meal->
                        categoryMealLiveData.postValue(meal.meals)

                    }
                }

            override fun onFailure(call: Call<categoryMeal>, t: Throwable) {
                Log.e("err",t.message.toString())
                }

        })

    }

}