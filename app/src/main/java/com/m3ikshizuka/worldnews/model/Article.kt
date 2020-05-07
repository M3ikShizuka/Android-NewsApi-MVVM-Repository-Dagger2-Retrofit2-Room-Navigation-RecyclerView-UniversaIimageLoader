package com.m3ikshizuka.worldnews.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*
-{
    -"source": {},
    "author": "BBC News",
    "title": "Massive surge in deaths in Ecuador's largest city",
    "description": "Guayas province, home to the city of Guayaquil, sees nearly 6,000 extra deaths in two-week period.",
    "url": "http://www.bbc.co.uk/news/world-latin-america-52324218",
    "urlToImage": "https://ichef.bbci.co.uk/images/ic/1024x576/p089cbp5.jpg",
    "publishedAt": "2020-04-17T10:31:26Z",
    "content": "Media captionMany families in Ecuador's biggest city are waiting to bury their loved ones\r\nEcuador's official coronavirus death toll is 403, but new figures from one province suggest thousands have died.\r\nThe government said 6,700 people died in Guayas provin… [+1753 chars]"
}
 */

@Entity(indices = [Index(value = ["title"], unique = true)])
data class Article (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Embedded
    var source: Source?,
    @field:SerializedName("author")
    var author: String?,
    @field:SerializedName("title")
    var title: String?,
    @field:SerializedName("description")
    var description: String?,
    @field:SerializedName("url")
    var url: String?,
    @field:SerializedName("urlToImage")
    var urlToImage: String?,
    @field:SerializedName("publishedAt")
    var publishedAt: String?,
    @field:SerializedName("content")
    var content: String?
)