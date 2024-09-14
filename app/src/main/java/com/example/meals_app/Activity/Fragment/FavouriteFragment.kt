package com.example.meals_app.Activity.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meals_app.Activity.MealActivity
import com.example.meals_app.Adapter.FavouriteAdapter
import com.example.meals_app.Data.Meal
import com.example.meals_app.Database.MealsDatabase
import com.example.meals_app.R
import com.example.meals_app.viewModel.MealViewFactory
import com.example.meals_app.viewModel.MealViewModel

class FavouriteFragment : Fragment() {
    private lateinit var mealViewModel: MealViewModel
    private lateinit var recyclerViewSaved: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView
        recyclerViewSaved = view.findViewById(R.id.recycler_view_saved)
        recyclerViewSaved.layoutManager = LinearLayoutManager(context)

        // Get instance from database
        val database = MealsDatabase.getDatabase(requireContext())
        mealViewModel = ViewModelProvider(this, MealViewFactory(database)).get(MealViewModel::class.java)

        // Observe data and set up the adapter
        mealViewModel.getMealLiveData().observe(viewLifecycleOwner, { meals ->
            Log.d("FavouriteFragment", "Meals: $meals")
            recyclerViewSaved.adapter = FavouriteAdapter(meals) { mealId ->
              //  fetchMealDetailsAndNavigate(mealId)
            }
        })
    }

    /*private fun fetchMealDetailsAndNavigate(mealId: String) {

        val api = Retrofit_Helper.api
        api.getMealByID(mealId).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.get(0)
                if (meal != null) {
                    val intent = Intent(requireContext(), MealActivity::class.java).apply {
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
                // Handle failure
            }
        })
    }*/
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


    private fun navigateToMealActivity(mealId: String) {
        val intent = Intent(requireContext(), MealActivity::class.java).apply {
            putExtra("meal_id", mealId)
        }
        startActivity(intent)
    }
}
