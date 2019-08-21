package com.codingwithmitch.foodrecipes.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Recipe(
    val title: String,
    val publisher: String,
    val ingredients: List<String>,
    @SerializedName("recipe_id")
    val recipeId: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("social_rank")
    val socialRank: Float
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(publisher)
        parcel.writeStringList(ingredients)
        parcel.writeString(recipeId)
        parcel.writeString(imageUrl)
        parcel.writeFloat(socialRank)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}