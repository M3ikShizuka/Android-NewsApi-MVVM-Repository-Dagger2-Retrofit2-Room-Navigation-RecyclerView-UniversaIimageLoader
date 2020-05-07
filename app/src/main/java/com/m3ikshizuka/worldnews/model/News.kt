package com.m3ikshizuka.worldnews.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/*
{
    "status": "ok",
    "totalResults": 10,
    -"articles": []
}
 */

@Entity
data class News(
    val articles: List<Article>,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("totalResults")
    val totalResults: Int
)