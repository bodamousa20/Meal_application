package com.example.meals_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealDATA
import com.example.meals_app.Data.Mealcat
import com.example.meals_app.Data.categoryMeal
import com.example.meals_app.Retrofit.Retrofit_Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountaryMealsViewModel(application: Application) : AndroidViewModel(application) {

    private val _mealsLiveData = MutableLiveData<List<Mealcat>>()
    val mealsLiveData: LiveData<List<Mealcat>> = _mealsLiveData

    fun getMealsByCountry(country: String) {
        Retrofit_Helper.api.getMealsByCountary(country).enqueue(object : Callback<categoryMeal> { // Update call to match data type
            override fun onResponse(call: Call<categoryMeal>, response: Response<categoryMeal>) {
                if (response.isSuccessful) {
                    _mealsLiveData.postValue(response.body()?.meals ?: emptyList()) // Correct
                }
            }

            override fun onFailure(call: Call<categoryMeal>, t: Throwable) {
                // Handle error
            }
        })
    }
}
