package com.codingwithmitch.foodrecipes.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.codingwithmitch.foodrecipes.models.Recipe
import com.codingwithmitch.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel: ViewModel() {

    private val mRecipeRepository = RecipeRepository.getInstance()
    private var mIsViewingRecipes = false
    private var mIsPerformingQuery = false

    fun getRecipes(): LiveData<List<Recipe>> {
        return mRecipeRepository.getRecipes()
    }

    fun searchRecipesApi(query: String, pageNumber: Int) {
        mIsViewingRecipes = true
        mIsPerformingQuery = true
        mRecipeRepository.searchRecipesApi(query, pageNumber)
    }

    fun isViewingRecipes(): Boolean {
        return mIsViewingRecipes
    }

    fun setIsViewingRecipes(isViewingRecipes: Boolean) {
        mIsViewingRecipes = isViewingRecipes
    }

    fun isPerformingQuery(): Boolean {
        return mIsPerformingQuery
    }

    fun setIsPerformingQuery(isPerformingQuery: Boolean) {
        mIsPerformingQuery = isPerformingQuery
    }

    fun searchNextPage() {
        if (!mIsPerformingQuery && mIsViewingRecipes) {
            mRecipeRepository.searchNextPage()
        }
    }

    fun onBackPressed(): Boolean {
        if (mIsPerformingQuery) {
            // cancel the query
            mRecipeRepository.cancelRequest()
            mIsPerformingQuery = false
        }
        return if (mIsViewingRecipes) {
            mIsViewingRecipes = false
            false
        } else {
            true
        }
    }
}