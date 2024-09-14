package com.example.meals_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class CategoryMealsViewModel: ViewModel() {
    val categoryMealLiveData = MutableLiveData<List<Mealcat>>()

     val _mealDetails = MutableLiveData<Meal?>()


    // fetch meal by api and the put the value inside live data
    fun fetchMealById(mealId: String) {

        viewModelScope.launch(Dispatchers.IO) {
          val meal = Retrofit_Helper.api.getMealByID(mealId).meals.get(0)
         withContext(  Dispatchers.Main){
             _mealDetails.postValue(meal)
         }
        }
    }



    fun getMealByCatName(catName:String){
        viewModelScope.launch {
           val meal =  Retrofit_Helper.api.getMealsByCategory(catName).meals
            withContext(Dispatchers.Main){
                categoryMealLiveData.postValue(meal)
            }


        }

    }

}