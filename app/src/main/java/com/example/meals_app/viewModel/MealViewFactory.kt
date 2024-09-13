package com.example.meals_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meals_app.Database.MealsDatabase

class MealViewFactory(private val mealsDatabase: MealsDatabase):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealsDatabase) as T
    }
}