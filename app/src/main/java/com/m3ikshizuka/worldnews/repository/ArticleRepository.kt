package com.m3ikshizuka.worldnews.repository

import androidx.lifecycle.MutableLiveData
import com.m3ikshizuka.worldnews.ThreadsManager
import com.m3ikshizuka.worldnews.db.ArticleDao
import com.m3ikshizuka.worldnews.model.Article
import com.m3ikshizuka.worldnews.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository
@Inject constructor(
    private val threadsManager: ThreadsManager,
    private val articleDao: ArticleDao
) {
    fun getArticleById(article: MutableLiveData<Resource<Article>>, articleId: Int) {
        object : NetworkBoundResource<Article, Article>(threadsManager) {
            override fun request(): Resource<Article> {
                return Resource.error("This is stub!", null)
            }

            override fun responseDataHandler(data: Resource<Article>): Resource<Article> {
                return Resource.error("This is stub!", null)
            }

            override fun saveDataToDatabase(data: Resource<Article>) {
                return
            }

            override fun requestToDatabase(): Resource<Article> {
                val articleStored = articleDao.getArticleById(articleId)

                articleStored?.let {
                    return Resource.success(it)
                } ?: run {
                    return Resource.error("Data not found!", articleStored)
                }
            }

            override fun setChange(value: Resource<Article>) {
                if (article.value != value) {
                    article.postValue(value)
                }
            }
        }.getDataFromStorage()
    }
}