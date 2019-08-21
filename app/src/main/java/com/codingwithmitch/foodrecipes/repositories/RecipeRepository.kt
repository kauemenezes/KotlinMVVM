package com.codingwithmitch.foodrecipes.repositories

import android.arch.lifecycle.LiveData
import com.codingwithmitch.foodrecipes.models.Recipe
import com.codingwithmitch.foodrecipes.requests.RecipeApiClient

class RecipeRepository {

    private val mRecipeApiClient = RecipeApiClient.getInstance()
    private var mQuery: String? = null
    private var mPageNumber = 0

    fun getRecipes(): LiveData<List<Recipe>> {
        return mRecipeApiClient.getRecipes()
    }

    fun searchRecipesApi(query: String, page: Int) {
        val pageNumber = if (page == 0) 1 else page
        mQuery = query
        mPageNumber = page
        mRecipeApiClient.searchRecipesApi(query, pageNumber)
    }

    fun cancelRequest() {
        mRecipeApiClient.cancelRequest()
    }

    fun searchNextPage() {
        searchRecipesApi(mQuery!!, mPageNumber + 1)
    }

    companion object {

        private var instance: RecipeRepository? = null

        fun getInstance(): RecipeRepository {
            if (instance == null) {
                instance = RecipeRepository()
            }
            return instance!!
        }
    }
}
