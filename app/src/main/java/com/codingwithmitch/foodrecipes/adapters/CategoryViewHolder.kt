package com.codingwithmitch.foodrecipes.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_category_list_item.view.*

class CategoryViewHolder (itemView: View, private val onRecipeListener: OnRecipeListener): RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    var categoryTitle = itemView.category_title as TextView
    var categoryImage = itemView.category_image as CircleImageView

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onRecipeListener.onCategoryClick(categoryTitle.text.toString())
    }
}