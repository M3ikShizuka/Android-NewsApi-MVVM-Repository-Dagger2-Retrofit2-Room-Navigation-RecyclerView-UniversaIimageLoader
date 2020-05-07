package com.m3ikshizuka.worldnews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.m3ikshizuka.worldnews.model.Article
import javax.inject.Singleton

@Singleton
@Database(entities = arrayOf(Article::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}