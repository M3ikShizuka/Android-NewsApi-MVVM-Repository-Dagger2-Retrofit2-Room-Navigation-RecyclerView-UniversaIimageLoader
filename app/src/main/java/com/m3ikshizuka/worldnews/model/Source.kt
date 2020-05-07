package com.m3ikshizuka.worldnews.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/*
-"source": {
    "id": "bbc-news",
    "name": "BBC News"
},
*/

@Entity
data class Source (
    @field:SerializedName("Id")
    var sourceId: String?,
    @field:SerializedName("name")
    var sourceName: String?
)