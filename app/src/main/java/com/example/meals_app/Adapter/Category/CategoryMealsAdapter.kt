package com.example.meals_app.Adapter.Category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals_app.Data.Mealcat
import com.example.meals_app.R


class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealsList = ArrayList<Mealcat>()


    var onMealClick: ((Mealcat) -> Unit)? = null // define var of Meal cat obj

    class CategoryMealsViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImageView: ImageView = itemView.findViewById(R.id.meal_image)
        val categoryTextView: TextView = itemView.findViewById(R.id.incredent_txt)
       // val loadingProgressBar:ProgressBar = itemView.findViewById(R.id.category_meal_loading)
   }

    fun setMeal(mealsList: List<Mealcat>){
        this.mealsList = mealsList as ArrayList<Mealcat>
        notifyDataSetChanged()
    }
            override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.meal_item_card, parent, false)
                return CategoryMealsViewModel(itemView)
    }


            override fun onBindViewHolder (holder: CategoryMealsViewModel, position: Int) {
                //holder.loadingProgressBar.visibility = View.GONE
                if (position < mealsList.size) {
                    val mealCat = mealsList[position]
                    Glide.with(holder.itemView).load(mealCat.strMealThumb).into(holder.categoryImageView)
                    holder.categoryTextView.text = mealCat.strMeal

                    holder.itemView.setOnClickListener {
                        onMealClick?.invoke(mealCat)   // assign to Meal cat to the object
                    }
                } else {
                    Log.e("CategoryMealsAdapter", "Position $position is out of bounds")
                }
            }
            override fun getItemCount(): Int {
        return mealsList.size
    }
}