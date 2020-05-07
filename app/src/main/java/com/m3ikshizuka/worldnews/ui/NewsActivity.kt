package com.m3ikshizuka.worldnews.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.m3ikshizuka.worldnews.LOG_APP
import com.m3ikshizuka.worldnews.R

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity)
        Log.d(LOG_APP, "NewsActivity::onCreate()")
    }
}