package com.codingwithmitch.foodrecipes.requests

import com.codingwithmitch.foodrecipes.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    companion object {

        private val retrofitBuilder =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

        private val retrofit = retrofitBuilder.build()

        private val recipeApi = retrofit.create(RecipeApi::class.java)

        fun getRecipeApi(): RecipeApi {
            return recipeApi
        }
    }
}