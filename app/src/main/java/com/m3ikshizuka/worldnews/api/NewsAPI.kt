package com.m3ikshizuka.worldnews.api

import com.m3ikshizuka.worldnews.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val NEWAPI_DOMAIN = "http://newsapi.org/"

interface NewsAPI {
    @Headers("x-api-key: 87ef4069c9734fccb5e8dc32bd0a83e7")
    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country: String = "us"): Call<News>
}