package com.example.meals_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals_app.Data.Meal
import com.example.meals_app.Database.MealDATA
import com.example.meals_app.R

class FavouriteAdapter(private val meals: List<MealDATA>, private val onMealClick:(String)->Unit) :
    RecyclerView.Adapter<FavouriteAdapter.SavedMealViewHolder>()  {


    class SavedMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImageView: ImageView = itemView.findViewById(R.id.meal_image)
        val categoryTextView: TextView = itemView.findViewById(R.id.incredent_txt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMealViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item_card, parent, false)
        return SavedMealViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: SavedMealViewHolder, position: Int) {
        val meal = meals[position]
        holder.categoryTextView.text = meal.strMeal
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.categoryImageView)
        holder.itemView.setOnClickListener {
            val mealId = meal.idMeal // Replace with the actual way you retrieve the meal ID
            onMealClick(mealId)
        }
    }

}