package com.example.meals_app.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals_app.Activity.MealActivity
import com.example.meals_app.CountaryMealsActivity
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealList
import com.example.meals_app.Data.Mealcat
import com.example.meals_app.Data.categoryMeal
import com.example.meals_app.Retrofit.Retrofit_Helper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryMealsViewModel : ViewModel() {

    val CountryLiveData = MutableLiveData<List<Mealcat>>()
    val CountaryMealDetails = MutableLiveData<Meal>()

    fun getMealsByCountry(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val meal =
                    Retrofit_Helper.api.getMealsByCountary(country).meals

                withContext(Dispatchers.Main) {
                    CountryLiveData.postValue(meal)
                }
            } catch (e: Exception) {
                Log.e("CountryMealsViewModel", "Error fetching  meal: ${e.message}")
            }

        }
    }


     fun fetchMealDetails(mealId: String) {
         try {

             viewModelScope.launch(Dispatchers.IO) {
                 val MealDetails = Retrofit_Helper.api.getMealByID(mealId).meals.get(0)

                 withContext(Dispatchers.Main) {
                     CountaryMealDetails.postValue(MealDetails)
                 }
             }
         }catch (e:Exception){
             Log.e("meal error","fetchMealDetails: Eror in fetching Meals Details ${e.message} ")
         }
     }


}
