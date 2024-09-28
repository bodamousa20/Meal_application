package com.example.meals_app.Activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals_app.Database.MealDATA
import com.example.meals_app.Database.MealsDatabase
import com.example.meals_app.R
import com.example.meals_app.adapters.IngredientsAdapter
import com.example.meals_app.viewModel.MealViewFactory
import com.example.meals_app.viewModel.MealViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MealActivity : AppCompatActivity() {
    private var isFavorite: Boolean = false

    private lateinit var mealName: String
    private lateinit var mealImage: String
    private lateinit var mealInstructions: String
    private lateinit var mealIngredients: List<String>
    private lateinit var category: String
    private lateinit var area: String
    private lateinit var youtubePlayer: String

    private lateinit var mealViewModel: MealViewModel

    private lateinit var mealNameTextView: TextView
    private lateinit var categoryTxt: TextView
    private lateinit var areaTxt: TextView
    private lateinit var appBarImageView: ImageView
    private lateinit var instructionContentTextView: TextView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var youtubePlayerFrame: WebView
    lateinit var context :Context

    private lateinit var favoriteButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meal)
        // Find views
        mealNameTextView = findViewById(R.id.meal_name)
        appBarImageView = findViewById(R.id.app_bar_image)
        categoryTxt = findViewById(R.id.cat_txt)
        areaTxt = findViewById(R.id.area)
        youtubePlayerFrame = findViewById(R.id.web_youtube)
        instructionContentTextView = findViewById(R.id.tv_instruction_content)
        ingredientsRecyclerView = findViewById(R.id.rv_ingredients)
        favoriteButton = findViewById(R.id.fab_favorite)

        // Initialize ViewModel
        val database = MealsDatabase.getDatabase(applicationContext)
        mealViewModel = ViewModelProvider(this, MealViewFactory(database)).get(MealViewModel::class.java)

        // Retrieve data from the intent
        getMealInformation()
        val mealId = intent.getStringExtra("meal_id").toString()

        checkIfMealIsFavorite(mealId)

        // Set up favorite button click
        favoriteButton.setOnClickListener {
            handleFavoriteButtonClick()
        }

        // Check if the meal is already in favorites and update button state
    }

    private fun handleFavoriteButtonClick() {
        val mealId = intent.getStringExtra("meal_id").toString()
        val meal = MealDATA(
            idMeal = mealId,
            strMeal = mealName,
            strMealThumb = mealImage
        )
        if (isFavorite) {
            // Remove from favorites
            mealViewModel.DeleteMeal(meal)
            favoriteButton.setImageResource(R.drawable.notfavicon) // Change icon to not favorited
            showToast("Removed from favorite")
            isFavorite = false
        } else {
            // Add to favorites
            mealViewModel.SaveMeal(meal)
            favoriteButton.setImageResource(R.drawable.favourite) // Change icon to favorited
            showToast("Added to favorite")
            favoriteButton.setBackgroundColor(Color.RED)

            isFavorite = true
        }

    }
    private fun checkIfMealIsFavorite(mealId: String) {
        mealViewModel.isMealFavorite(mealId).observe(this, Observer { meal ->
            if (meal != null) {
                // Meal is in favorites
                isFavorite = true
                favoriteButton.setColorFilter(ContextCompat.getColor(this, R.color.primary)) // Change icon color to red
            } else {
                // Meal is not in favorites
                isFavorite = false
                favoriteButton.setImageResource(R.drawable.notfavicon) // Change icon to not favorited
            }
        })
    }




    private fun getMealInformation() {

        mealName = intent.getStringExtra("meal_name").toString()
        mealImage = intent.getStringExtra("meal_image").toString()
        mealInstructions = intent.getStringExtra("meal_instructions").toString()
        mealIngredients = intent.getStringArrayListExtra("meal_ingredients") ?: listOf()
        category = intent.getStringExtra("category").toString()
        area = intent.getStringExtra("Area").toString()
        youtubePlayer = intent.getStringExtra("youtube").toString()

        // Populate the UI elements
        mealNameTextView.text = mealName
        areaTxt.text = area
        categoryTxt.text = category

        // Setup YouTube player
        if (youtubePlayer.isNotEmpty()) {
            val videoId = youtubePlayer.substringAfter("v=").substringBefore("&")
            val videoUrl = "https://www.youtube.com/embed/$videoId"
            youtubePlayerFrame.settings.javaScriptEnabled = true
            youtubePlayerFrame.settings.domStorageEnabled = true
            youtubePlayerFrame.webChromeClient = WebChromeClient()
            youtubePlayerFrame.loadUrl(videoUrl)
        }

        Glide.with(this).load(mealImage).into(appBarImageView)
        instructionContentTextView.text = mealInstructions
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        ingredientsRecyclerView.adapter = IngredientsAdapter(mealIngredients)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
