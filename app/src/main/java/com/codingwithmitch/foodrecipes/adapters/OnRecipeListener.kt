package com.codingwithmitch.foodrecipes.adapters

interface OnRecipeListener {

    fun onRecipeClick(position: Int)

    fun onCategoryClick(category: String)
}