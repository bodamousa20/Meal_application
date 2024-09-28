package com.example.meals_app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals_app.R

class IngredientsAdapter(private val ingredients: List<String>) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientText: TextView = itemView.findViewById(R.id.incredent_txt)
        private val ingredientImage: ImageView = itemView.findViewById(R.id.ingredient_image)

        fun bind(ingredient: String) {
            ingredientText.text = ingredient

            // Generate the image URL based on the ingredient name
            val ingredientImageUrl = "https://www.themealdb.com/images/ingredients/${ingredient}.png"
            Log.i("imgredient image", ingredientImageUrl);
            // Load the image using Glide
            Glide.with(itemView.context)
                .load(ingredientImageUrl)
                .into(ingredientImage)
        }
    }
}
