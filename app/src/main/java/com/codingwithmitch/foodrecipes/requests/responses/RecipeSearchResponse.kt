package com.codingwithmitch.foodrecipes.requests.responses

import com.codingwithmitch.foodrecipes.models.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("recipes")
    val recipes: List<Recipe>
)