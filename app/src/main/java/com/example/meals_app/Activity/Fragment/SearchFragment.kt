package com.example.meals_app.Activity.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider // Correct import statement
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meals_app.Activity.MealActivity
import com.example.meals_app.Adapter.SearchMealsAdapter
import com.example.meals_app.Data.Meal
import com.example.meals_app.ViewModel.SearchViewModel
import com.example.meals_app.databinding.FragmentSearchBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel:SearchViewModel
    private lateinit var adapter: SearchMealsAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // Initialize the SearchMealsAdapter without click handling initially
        adapter = SearchMealsAdapter()
        binding.serarchRv.layoutManager = LinearLayoutManager(context)
        binding.serarchRv.adapter = adapter

        // Set up the SearchView to listen for text input
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMeals(it)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchJob?.cancel()  // Cancel the previous search job if it exists
                    searchJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(300) // Debounce delay
                        viewModel.searchMeals(it)
                    }
                }
                return true
            }
        })

        // Observe the LiveData from the ViewModel to update the adapter with new search results
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.setMeals(meals)
        }
        adapter.onClickedItem= {meal->
            val intent = Intent(context, MealActivity::class.java).apply {
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
}

private fun getIngredientsList(meal: Meal): ArrayList<String> {
    return arrayListOf(
        meal.strIngredient1,
        meal.strIngredient2,
        meal.strIngredient3,
        meal.strIngredient4,
        meal.strIngredient5,
        meal.strIngredient6,
        meal.strIngredient7,
        meal.strIngredient8,

    ).filterNotNull() as ArrayList<String>
}
