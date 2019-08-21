package com.codingwithmitch.foodrecipes.requests

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.codingwithmitch.foodrecipes.AppExecutors
import com.codingwithmitch.foodrecipes.models.Recipe
import com.codingwithmitch.foodrecipes.util.NETWORK_TIMEOUT
import java.util.concurrent.TimeUnit
import com.codingwithmitch.foodrecipes.requests.responses.RecipeSearchResponse
import com.codingwithmitch.foodrecipes.util.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipeApiClient {

    private val TAG = "RecipeApiClient"
    private val mRecipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    private var call: Call<RecipeSearchResponse>? = null

    fun getRecipes(): LiveData<List<Recipe>> {
        return mRecipes
    }

    fun searchRecipesApi(query: String, pageNumber: Int) {
        call = ServiceGenerator.getRecipeApi().searchRecipe(
            API_KEY,
            query,
            pageNumber.toString())

        call?.enqueue(object : Callback<RecipeSearchResponse> {

            override fun onResponse(call: Call<RecipeSearchResponse>, response: Response<RecipeSearchResponse>) {
                if (response.code() == 200) {
                    val list = (response.body() as RecipeSearchResponse).recipes
                    if (pageNumber == 1) {
                        mRecipes.postValue(list)
                    } else {
                        val currentRecipes = mRecipes.value as MutableList<Recipe>
                        currentRecipes.addAll(list)
                        mRecipes.postValue(currentRecipes)
                    }
                } else {
                    val error = response.errorBody()?.string()
                    Log.e(TAG, "run: error: $error")
                    mRecipes.postValue(null)
                }
            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable?) {
                if (call.isCanceled) {
                    Log.e(TAG, "canceled request")
                } else {
                    Log.e(TAG, "run: error: ${t!!.message}")
                }
                mRecipes.postValue(null)
            }
        })


//        if(mRetrieveRecipesRunnable != null){
//            mRetrieveRecipesRunnable = null
//        }

//        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query, pageNumber)
//
//        val handler = AppExecutors.getInstance().networkIO.submit(mRetrieveRecipesRunnable)

//        try {
//            handler.get(NETWORK_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
//        } catch (e: Exception){
//            e.printStackTrace()
//            handler.cancel(true)
//        }
//        AppExecutors.getInstance().networkIO.schedule(object: Runnable {
//            override fun run() {
//                // let the user know it timed out
//                handler.cancel(true)
//            }
//        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    fun cancelRequest() {
        call?.cancel()
    }

    companion object {

        private var instance: RecipeApiClient? = null

        fun getInstance(): RecipeApiClient {
            if (instance == null) {
                instance = RecipeApiClient()
            }
            return instance!!
        }
    }

    private inner class RetrieveRecipesRunnable(
        private val query: String,
        private val pageNumber: Int
    ) : Runnable {
        private var cancelRequest: Boolean = false

        override fun run() {

            try {
                val response = getRecipes(query, pageNumber).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {
                    val list = (response.body() as RecipeSearchResponse).recipes
                    if (pageNumber == 1) {
                        mRecipes.postValue(list)
                    } else {
                        val currentRecipes = mRecipes.value as MutableList<Recipe>
                        currentRecipes.addAll(list)
                        mRecipes.postValue(currentRecipes)
                    }
                } else {
                    val error = response.errorBody()?.string()
                    Log.e(TAG, "run: error: $error")
                    mRecipes.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mRecipes.postValue(null)
            }

        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return ServiceGenerator.getRecipeApi().searchRecipe(
                API_KEY,
                query,
                pageNumber.toString()
            )
        }

        private fun cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the retrieval query")
            cancelRequest = true
        }
    }
}