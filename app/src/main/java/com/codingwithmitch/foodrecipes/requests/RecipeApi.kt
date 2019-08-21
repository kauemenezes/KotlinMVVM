package com.codingwithmitch.foodrecipes.requests

import com.codingwithmitch.foodrecipes.requests.responses.RecipeResponse
import com.codingwithmitch.foodrecipes.requests.responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    // Search
    @GET("api/search")
    fun searchRecipe(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("page") page: String
    ): Call<RecipeSearchResponse>

    // Get recipe request
    @GET("api/get")
    fun getRecipe(
        @Query("key") key: String,
        @Query("rId") recipeId: String
    ): Call<RecipeResponse>
}