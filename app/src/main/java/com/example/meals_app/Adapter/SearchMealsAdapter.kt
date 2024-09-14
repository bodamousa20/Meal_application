package com.example.meals_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meals_app.Data.Meal
import com.example.meals_app.R
import com.bumptech.glide.Glide
import com.example.meals_app.Data.CategoryItem
import com.example.meals_app.Data.Mealcat

class SearchMealsAdapter() : RecyclerView.Adapter<SearchMealsAdapter.MealViewHolder>() {

    private var meals = listOf<Meal>()
    var onClickedItem : ((Meal)->Unit)? = null

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealImage: ImageView = itemView.findViewById(R.id.meal_image)
        private val mealName: TextView = itemView.findViewById(R.id.incredent_txt)

        fun bind(meal: Meal) {
            mealName.text = meal.strMeal
            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .into(mealImage)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_item_card, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
        holder.itemView.setOnClickListener{
            onClickedItem?.invoke(meals[position])
        }
    }

    override fun getItemCount(): Int = meals.size

    fun setMeals(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}
