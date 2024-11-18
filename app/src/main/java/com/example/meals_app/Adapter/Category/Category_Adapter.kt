package com.example.meals_app.Adapter.Category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals_app.Data.CategoryItem
import com.example.meals_app.R


class Category_Adapter(    private var categoryList : List<CategoryItem>
):RecyclerView.Adapter<Category_Adapter.CategoryViewHolder>() {
    var onClickedItem : ((CategoryItem)->Unit)? = null

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImageView: ImageView = itemView.findViewById(R.id.category_image)
        val categoryTextView: TextView = itemView.findViewById(R.id.category_txtt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(itemView)

    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val CurentItem = categoryList[position]
        Glide.with(holder.itemView.context)
            .load(CurentItem.strCategoryThumb)
            .into(holder.categoryImageView)
         holder.categoryTextView.text= CurentItem.strCategory

        holder.itemView.setOnClickListener{
            onClickedItem?.invoke(categoryList[position])
        }
            }
    fun setCategotyList(categoryList:List<CategoryItem>){
        this.categoryList = categoryList as ArrayList<CategoryItem>
        notifyDataSetChanged()
    }
}