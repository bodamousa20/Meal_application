package com.example.meals_app.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals_app.Data.Meal
import com.example.meals_app.Retrofit.Retrofit_Helper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteViewModel:ViewModel() {
    var mealLiveDataFavourite = MutableLiveData<Meal>()  //

    fun fetchMealDetails(mealid:String){
        viewModelScope.launch {
            try {
                val meal = Retrofit_Helper.api.getMealByID(mealid).meals.get(0)
                withContext(Dispatchers.Main) {
                    mealLiveDataFavourite.postValue(meal)
                    Log.e("Fav MEALS FETCH ", mealLiveDataFavourite.toString())
                }
            }
            catch (e:Exception){
                Log.e("err", "Error fetching meal: ${e.message}")
            }

        }
    }
}