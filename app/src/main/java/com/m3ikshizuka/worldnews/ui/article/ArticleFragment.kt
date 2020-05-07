package com.m3ikshizuka.worldnews.ui.article

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.m3ikshizuka.worldnews.App
import com.m3ikshizuka.worldnews.LOG_APP
import com.m3ikshizuka.worldnews.R
import com.m3ikshizuka.worldnews.common.LoadImage
import com.m3ikshizuka.worldnews.common.Time
import com.m3ikshizuka.worldnews.interfaces.ViewInterface
import kotlinx.android.synthetic.main.article_fragment.*
import javax.inject.Inject

class ArticleFragment() : Fragment(), ViewInterface {
    companion object {
        fun newInstance() = ArticleFragment()
    }

    val args: ArticleFragmentArgs by navArgs()
    @Inject
    lateinit var viewModel: ArticleViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_APP, "ArticleFragment::onAttach()")
        // inject di.
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set toolbar up button
        this.enableToolBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return this.findNavController().navigateUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.viewModel.getArticle().observe(this.viewLifecycleOwner, Observer { articleResource ->
            if (articleResource.data == null) {
                return@Observer
            }

            val article = articleResource.data!!

            // Image
            article.urlToImage?.let {url ->
                // Load image
                articleProgressBarLoad.visibility = View.VISIBLE
                LoadImage.getInstance()
                    .loadImage(url, articleImage) {
                        // Disable progress bar when loading is done.
                        articleProgressBarLoad.visibility = View.GONE
                    }
            } ?: run {
                // Image not exist
                articleProgressBarLoad.visibility = View.GONE
            }

            // Title
            articleTitle.text = article.title ?: "It hasn't title!"
            // Author
            articleAuthor.text = article.author ?: "Unknown author"

            // Date
            article.publishedAt?.let { date ->
                articleDate.text = Time.convertTimeFormat(date)
            } ?: run {
                articleDate.text = "Unknown date"
            }

            // Content
            articleContent.text = article.content ?: ""
        })

        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.viewModel.getArticleById(args.articleId)
    }
}