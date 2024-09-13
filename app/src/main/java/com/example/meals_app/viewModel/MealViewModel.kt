package com.example.meals_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealDATA
import com.example.meals_app.Database.MealsDAO
import com.example.meals_app.Database.MealsDatabase
import kotlinx.coroutines.launch

class MealViewModel(private val mealDatabase: MealsDatabase):ViewModel(){
    private val allFavoriteMealsLiveData: LiveData<List<MealDATA>> = mealDatabase.mealDao().getAllFavoriteMeals()

    fun getMealLiveData():LiveData<List<MealDATA>>{
        return allFavoriteMealsLiveData
    }
    fun SaveMeal(meal: MealDATA){
        viewModelScope.launch {
            mealDatabase.mealDao().saveMeal(meal)
        }
    }

    fun DeleteMeal(meal: MealDATA){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }
    fun isMealFavorite(mealId: String): LiveData<MealDATA> {
        return mealDatabase.mealDao().getMealById(mealId)
    }

}