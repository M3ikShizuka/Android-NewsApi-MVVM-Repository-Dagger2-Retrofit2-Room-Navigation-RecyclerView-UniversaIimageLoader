package com.m3ikshizuka.worldnews.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.m3ikshizuka.worldnews.model.Article
import com.m3ikshizuka.worldnews.repository.ArticleRepository
import com.m3ikshizuka.worldnews.vo.Resource
import javax.inject.Inject

class ArticleViewModel
@Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private var article: MutableLiveData<Resource<Article>> = MutableLiveData()

    fun getArticleById(articleId: Int) {
        this.articleRepository.getArticleById(article, articleId)
    }

    fun getArticle(): LiveData<Resource<Article>> {
        return this.article
    }
}