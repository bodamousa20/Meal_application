package com.example.meals_app.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.Mealcat
import com.example.meals_app.R

class MealsAdapter(private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    private var mealsList = listOf<Mealcat>()

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealName: TextView = itemView.findViewById(R.id.incredent_txt)
        val mealImage: ImageView = itemView.findViewById(R.id.meal_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item_card, parent, false)
        return MealViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealsList[position]
        holder.mealName.text = meal.strMeal
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.mealImage)
        holder.itemView.setOnClickListener {
            onItemClick(meal.idMeal) // Trigger the click event
        }
    }

    override fun getItemCount(): Int = mealsList.size

    fun setMeals(meals: List<Mealcat>) {
        mealsList = meals
        notifyDataSetChanged()
    }
}
