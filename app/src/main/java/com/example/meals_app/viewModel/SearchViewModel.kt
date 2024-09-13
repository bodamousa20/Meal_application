package com.example.meals_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealList
import com.example.meals_app.Retrofit.Retrofit_Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel :ViewModel() {
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun searchMeals(query: String) {
        Retrofit_Helper.api.searchMeal(query).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    _meals.value = (response.body()?.meals ?: emptyList())
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // Handle error
            }
        })
    }
}
