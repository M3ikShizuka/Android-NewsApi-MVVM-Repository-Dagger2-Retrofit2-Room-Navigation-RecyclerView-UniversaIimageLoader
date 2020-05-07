package com.m3ikshizuka.worldnews.ui.news

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.m3ikshizuka.worldnews.R
import com.m3ikshizuka.worldnews.common.LoadImage
import com.m3ikshizuka.worldnews.common.Time
import com.m3ikshizuka.worldnews.model.Article
import com.m3ikshizuka.worldnews.vo.Resource
import kotlinx.android.synthetic.main.item_news.view.*

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticlesHolder>() {
    private var articleList: List<Article> = ArrayList()

    // Create ViewHolder and init views for list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesHolder {
        return ArticlesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        )
    }

    // Bind views with content
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArticlesHolder, position: Int) {
        holder.bind(articleList[position])
    }

    // Get items count in list
    override fun getItemCount() = articleList.size

    // Update data and notify adapter that need update list
    fun refreshData(resourceArticles: Resource<List<Article>>) {
        if (resourceArticles.data == null) {
            return
        }

        val articles = resourceArticles.data!!
        this.articleList = articles
        this.notifyDataSetChanged()
    }

    inner class ArticlesHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(article: Article) = with(itemView) {
            itemView.setOnClickListener(this@ArticlesHolder)
            // Image
            article.urlToImage?.let {url ->
                // Load image
                itemView.newsProgressBarLoad.visibility = View.VISIBLE

                LoadImage.getInstance()
                    .loadImage(url, itemView.newsImage) { // itemView.newsImage
                        // Disable progress bar when loading is done.
                        itemView.newsProgressBarLoad.visibility = View.GONE
                    }
            } ?: run {
                // Image not exist
                itemView.newsProgressBarLoad.visibility = View.GONE
            }

            // Title
            itemView.newsTitle.text = article.title ?: "It hasn't title!"
            // Author
            itemView.newsAuthor.text = article.author ?: "Unknown author"

            // Date
            article.publishedAt?.let { date ->
                itemView.newsDate.text = Time.convertTimeFormat(date)
            } ?: run {
                itemView.newsDate.text = "Unknown date"
            }
        }

        override fun onClick(v: View?) {
            val article = articleList[this.adapterPosition]
            val action = NewsFragmentDirections.actionNewsFragmentToArticleFragment(article.id)
            v?.findNavController()?.navigate(action)
        }
    }
}