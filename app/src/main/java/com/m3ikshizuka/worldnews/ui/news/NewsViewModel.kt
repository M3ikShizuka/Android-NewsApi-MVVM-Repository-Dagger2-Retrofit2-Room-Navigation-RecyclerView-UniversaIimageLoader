package com.m3ikshizuka.worldnews.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.m3ikshizuka.worldnews.model.Article
import com.m3ikshizuka.worldnews.repository.NewsRepository
import com.m3ikshizuka.worldnews.vo.Resource
import javax.inject.Inject

class NewsViewModel
@Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var articleList: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    lateinit var articlesAdapter: ArticlesAdapter

    init {
        loadArticles()
    }

    fun getArticles(): LiveData<Resource<List<Article>>> {
        return this.articleList
    }

    fun loadArticles() {
        this.newsRepository.getNews(this.articleList)
    }
}