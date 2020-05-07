package com.m3ikshizuka.worldnews.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.m3ikshizuka.worldnews.LOG_NETOWRK_REST_ACTION
import com.m3ikshizuka.worldnews.ThreadsManager
import com.m3ikshizuka.worldnews.api.NewsAPI
import com.m3ikshizuka.worldnews.db.ArticleDao
import com.m3ikshizuka.worldnews.model.Article
import com.m3ikshizuka.worldnews.model.News
import com.m3ikshizuka.worldnews.vo.Resource
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository
@Inject constructor(
    private val threadsManager: ThreadsManager,
    private val newsAPI: NewsAPI,
    private val articleDao: ArticleDao
) {
    fun getNews(articleList: MutableLiveData<Resource<List<Article>>>) {
        object : NetworkBoundResource<List<Article>, News>(threadsManager) {
            override fun request(): Resource<News> {
                var resource: Resource<News>
                // SYNC
                try {
                    val response: Response<News> = newsAPI.getNews().execute()
                    if (!response.isSuccessful) {
                        // Response error
                        val responseCode = response.code()
                        Log.d(
                            LOG_NETOWRK_REST_ACTION,
                            "[ERROR] getNewsAPI().getNews() response error code: $responseCode"
                        )

                        resource = Resource.error(response.message(), null)
                    }
                    else {
                        // Success
                        val news = response.body()
                        resource = Resource.success(news)
                    }
                }
                catch(exception: IOException) {
                    val errorMessage = exception.localizedMessage;
                    Log.d(
                        LOG_NETOWRK_REST_ACTION,
                        "[ERROR] getNewsAPI().getNews() request exception code: ${exception.localizedMessage}"
                    )

                    resource = Resource.error(errorMessage ?: "Request exception.", null)
                }

                return resource
            }

            override fun responseDataHandler(data: Resource<News>): Resource<List<Article>> {
                lateinit var result: Resource<List<Article>>

                data.data?.let { news ->
                    val list: List<Article> = news.articles
                    result = Resource.success(list)
                } ?: run {
                    val list: List<Article> = emptyList()
                    result = Resource.success(list)
                }

                return result
            }

            override fun saveDataToDatabase(data: Resource<List<Article>>) {
                data.data?.let { articleList ->
                    if (articleList.isEmpty()) {
                        return
                    }

                    articleDao.insertList(articleList)
                }
            }

            override fun requestToDatabase(): Resource<List<Article>> {
                val articleListStored = articleDao.getArticles()

                if (articleListStored.isEmpty()) {
                    return Resource.error("Data not found!", articleListStored)
                }

                return Resource.success(articleListStored)
            }

            override fun setChange(value: Resource<List<Article>>) {
                if (articleList.value != value) {
                    articleList.postValue(value)
                }
            }
        }.getDataFromServer()
    }
}