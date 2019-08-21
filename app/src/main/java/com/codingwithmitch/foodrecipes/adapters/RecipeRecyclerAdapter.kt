package com.codingwithmitch.foodrecipes.adapters

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codingwithmitch.foodrecipes.R
import com.codingwithmitch.foodrecipes.models.Recipe
import com.codingwithmitch.foodrecipes.util.DEFAULT_SEARCH_CATEGORIES
import com.codingwithmitch.foodrecipes.util.DEFAULT_SEARCH_CATEGORY_IMAGES

class RecipeRecyclerAdapter(private val onRecipeListener: OnRecipeListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val RECIPE_TYPE = 1
    private val LOADING_TYPE = 2
    private val CATEGORY_TYPE = 3

    private var recipes: List<Recipe>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        var view: View?
        return when(i) {
            1 -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_recipe_list_item, viewGroup, false)
                RecipeViewHolder(view, onRecipeListener)
            }
            2 -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_loading_list_item, viewGroup, false)
                LoadingViewHolder(view)
            }
            3 -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_category_list_item, viewGroup, false)
                CategoryViewHolder(view, onRecipeListener)
            }
            else -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_recipe_list_item, viewGroup, false)
                RecipeViewHolder(view, onRecipeListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        recipes?.let {
            val itemViewType = getItemViewType(position)
            when (itemViewType) {
                RECIPE_TYPE -> {
                    (viewHolder as RecipeViewHolder).title.text = it[position].title
                    viewHolder.publisher.text = it[position].publisher
                    viewHolder.socialScore.text = Math.round(it[position].socialRank).toString()

                    val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
                    Glide.with(viewHolder.itemView.context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(it[position].imageUrl)
                        .into(viewHolder.image)
                }

                CATEGORY_TYPE -> {
                    (viewHolder as CategoryViewHolder).categoryTitle.text = it[position].title

                    val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
                    val path = Uri.parse("android.resource://com.codingwithmitch.foodrecipes/drawable/" + it[position].imageUrl)
                    Glide.with(viewHolder.itemView.context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(path)
                        .into(viewHolder.categoryImage)
                }

                else -> {}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (recipes!![position].socialRank == -1f) {

            CATEGORY_TYPE
        } else if (recipes!![position].title == "LOADING...") {

            LOADING_TYPE
        }
        else if (position == recipes!!.size - 1
            && position != 0
            && recipes!![position].title != "EXHAUSTED...") {

            LOADING_TYPE
        } else {

            RECIPE_TYPE
        }
    }

    fun displayLoading() {
        if (!isLoading()) {
            val recipe = Recipe("LOADING...", "", arrayListOf(), "", "", 0f)
            val loadingList = arrayListOf(recipe)
            recipes = loadingList
            notifyDataSetChanged()
        }
    }

    private fun isLoading(): Boolean {
        recipes?.let {
            if (it.isNotEmpty() && it[it.size -1].title == "LOADING...") {
                return true
            }
        }
        return false
    }

    fun displaySearchCategories() {
        val categories = arrayListOf<Recipe>()
        for (i in 0 until DEFAULT_SEARCH_CATEGORIES.size) {
            val recipe = Recipe(DEFAULT_SEARCH_CATEGORIES[i], "", arrayListOf(),
                "", DEFAULT_SEARCH_CATEGORY_IMAGES[i], -1f)
            categories.add(recipe)
        }
        recipes = categories
        notifyDataSetChanged()
    }

    fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }
}