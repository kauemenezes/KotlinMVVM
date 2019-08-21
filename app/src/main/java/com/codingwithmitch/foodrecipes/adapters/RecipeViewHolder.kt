package com.codingwithmitch.foodrecipes.adapters

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_recipe_list_item.view.*

class RecipeViewHolder(itemView: View, private val onRecipeListener: OnRecipeListener): RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    var title = itemView.recipe_title as TextView
    var publisher = itemView.recipe_publisher as TextView
    var socialScore = itemView.recipe_social_score as TextView
    var image = itemView.recipe_image as AppCompatImageView

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onRecipeListener.onRecipeClick(adapterPosition)
    }
}