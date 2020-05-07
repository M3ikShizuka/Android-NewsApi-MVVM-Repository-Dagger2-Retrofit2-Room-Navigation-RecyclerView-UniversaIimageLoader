package com.m3ikshizuka.worldnews.di

import com.m3ikshizuka.worldnews.App
import com.m3ikshizuka.worldnews.ui.article.ArticleFragment
import com.m3ikshizuka.worldnews.ui.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(fragment: NewsFragment)
    fun inject(fragment: ArticleFragment)
}