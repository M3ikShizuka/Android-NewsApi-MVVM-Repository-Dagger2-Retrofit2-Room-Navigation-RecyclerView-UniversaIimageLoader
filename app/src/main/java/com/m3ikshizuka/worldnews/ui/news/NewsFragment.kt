package com.m3ikshizuka.worldnews.ui.news

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.m3ikshizuka.worldnews.App
import com.m3ikshizuka.worldnews.LOG_APP
import com.m3ikshizuka.worldnews.R
import com.m3ikshizuka.worldnews.interfaces.ViewInterface
import com.m3ikshizuka.worldnews.model.Article
import com.m3ikshizuka.worldnews.vo.Resource
import com.m3ikshizuka.worldnews.vo.Status
import kotlinx.android.synthetic.main.news_fragment.*
import javax.inject.Inject

class NewsFragment : Fragment(), ViewInterface {
    companion object {
        fun newInstance() = NewsFragment()
    }

    @Inject
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_APP, "NewsFragment::onCreate()")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_APP, "NewsFragment::onAttach()")
        // inject di.
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.disableToolBar()
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(LOG_APP, "NewsFragment::onActivityCreated()")

        // Swipe
        swipeContainerNews.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.requireContext(),
            R.color.colorPrimary
        ))
        swipeContainerNews.setColorSchemeColors(Color.WHITE)

        swipeContainerNews.setOnRefreshListener {
            this.newsViewModel.loadArticles()
            swipeContainerNews.isRefreshing = false
        }

        // Init articles adapter and set to list
        this.newsViewModel.articlesAdapter = ArticlesAdapter()
        this.articlesRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        this.articlesRecyclerView.adapter = this.newsViewModel.articlesAdapter
        // Init observer
        this.newsViewModel.getArticles().observe(this.viewLifecycleOwner, Observer {
            it?.let {
                this.newsViewModel.articlesAdapter.refreshData(it)
                this.articleResourceHandler(it)
            }
        })
    }

    fun articleResourceHandler(articleListResource: Resource<List<Article>>) {
        when (articleListResource.status) {
            Status.LOADING -> {
                Toast.makeText(this.context, "Loading article list", Toast.LENGTH_LONG).show()
            }
            Status.ERROR -> {
                Toast.makeText(this.context, "Error load article list: ${articleListResource.message}", Toast.LENGTH_LONG).show()
            }
            else -> {

            }
        }
    }
}