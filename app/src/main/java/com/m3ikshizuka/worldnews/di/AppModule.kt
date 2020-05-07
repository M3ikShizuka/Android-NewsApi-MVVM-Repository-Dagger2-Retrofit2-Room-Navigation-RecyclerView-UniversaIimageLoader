package com.m3ikshizuka.worldnews.di

import androidx.room.Room
import com.m3ikshizuka.worldnews.App
import com.m3ikshizuka.worldnews.ThreadsManager
import com.m3ikshizuka.worldnews.api.NEWAPI_DOMAIN
import com.m3ikshizuka.worldnews.api.NewsAPI
import com.m3ikshizuka.worldnews.db.AppDatabase
import com.m3ikshizuka.worldnews.db.ArticleDao
import com.m3ikshizuka.worldnews.repository.ArticleRepository
import com.m3ikshizuka.worldnews.repository.NewsRepository
import com.m3ikshizuka.worldnews.ui.article.ArticleViewModel
import com.m3ikshizuka.worldnews.ui.news.NewsViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
open class AppModule (private val app: App) {
    @Singleton
    @Provides
    fun provideNewsAPI(): NewsAPI {
        return Retrofit.Builder()
            .baseUrl(NEWAPI_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRoom(): AppDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java, "database"
        ).build()
    }

    @Provides
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsAPI: NewsAPI, articleDao: ArticleDao): NewsRepository {
        return NewsRepository(ThreadsManager(), newsAPI, articleDao)
    }

    @Provides
    @Singleton
    fun provideThreadsManager(): ThreadsManager {
        return ThreadsManager()
    }

    @Provides
    fun provideNewsViewModel(newsRepository: NewsRepository): NewsViewModel {
        return NewsViewModel(newsRepository)
    }

    @Provides
    fun provideArticleViewModel(articleRepository: ArticleRepository): ArticleViewModel {
        return ArticleViewModel(articleRepository)
    }
}