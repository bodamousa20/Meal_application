        package com.example.meals_app.viewModel

        import android.util.Log
        import androidx.lifecycle.Lifecycle
        import androidx.lifecycle.LifecycleCoroutineScope
        import androidx.lifecycle.LiveData
        import androidx.lifecycle.MutableLiveData
        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.ViewModelStore
        import androidx.lifecycle.viewModelScope
        import com.example.meals_app.Data.CategoryItem
        import com.example.meals_app.Data.CategoryList
        import com.example.meals_app.Data.Countary_item
        import com.example.meals_app.Data.Countary_list
        import com.example.meals_app.Data.Meal
        import com.example.meals_app.Data.MealList
        import com.example.meals_app.Retrofit.Retrofit_Helper
        import kotlinx.coroutines.Dispatchers
        import kotlinx.coroutines.launch
        import kotlinx.coroutines.withContext
        import retrofit2.Call
        import retrofit2.Callback
        import retrofit2.Response

        class HomeViewModel : ViewModel(){

            var randomMealLiveData = MutableLiveData<Meal>()  //

            var categoryLiveData = MutableLiveData<List<CategoryItem>>()

             val _countryList = MutableLiveData<Countary_list>()



            fun fetchCountryList() {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val countryList = Retrofit_Helper.api.getAllCountary()
                        withContext(Dispatchers.Main) {
                            _countryList.postValue(countryList)
                        }
                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "Error fetching country list: ${e.message}")
                    }
                }
            }

            // get random meals
            fun getRandomMeal() {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val mealItem = Retrofit_Helper.api.getRandomMeal() // Assume we get the first meal
                        withContext(Dispatchers.Main) {
                            randomMealLiveData.postValue(mealItem.meals.get(0))
                        }
                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "Error fetching random meal: ${e.message}")
                    }
                }
            }

            // end of random meals



            // get category fun
            fun getAllCategory() {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val categoriesResponse = Retrofit_Helper.api.getAllCategory()
                        withContext(Dispatchers.Main) {
                            categoryLiveData.postValue(categoriesResponse.categories)
                        }
                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "Error fetching categories: ${e.message}")
                    }
                }
            }


        }