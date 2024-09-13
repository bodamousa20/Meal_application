package com.example.meals_app.Activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider // Correct import statement
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meals_app.Adapter.SearchMealsAdapter
import com.example.meals_app.ViewModel.SearchViewModel

import com.example.meals_app.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchMealsAdapter

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
                return false
            }
        })

        // Observe the LiveData from the ViewModel to update the adapter with new search results
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.setMeals(meals)
        }
    }
}
