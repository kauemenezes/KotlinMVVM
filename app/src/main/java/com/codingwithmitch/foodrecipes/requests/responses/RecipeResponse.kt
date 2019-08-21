package com.codingwithmitch.foodrecipes.requests.responses

import com.codingwithmitch.foodrecipes.models.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipe")
    val recipe: Recipe
)