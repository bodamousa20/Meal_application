package com.example.meals_app.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meals_app.Data.Countary_item
import com.example.meals_app.R
import com.example.meals_app.CountaryMealsActivity

class CountaryAdapter(
    private var countaryList: List<Countary_item>
) : RecyclerView.Adapter<CountaryAdapter.CountaryViewHolder>() {

     var onItemClickListener: ((Countary_item) -> Unit)? = null

    inner class CountaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countaryName: TextView = itemView.findViewById(R.id.countary_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountaryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.countary_card, parent, false)
        return CountaryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountaryViewHolder, position: Int) {
        val item = countaryList[position]
        holder.countaryName.text = item.strArea
         holder.itemView.setOnClickListener{
             onItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int = countaryList.size

    fun setCountaryList(list: List<Countary_item>) {
        countaryList = list
        notifyDataSetChanged()
    }
   /* fun setOnItemClickListener(listener: (Countary_item) -> Unit) {
        onItemClickListener = listener
    }*/
}
