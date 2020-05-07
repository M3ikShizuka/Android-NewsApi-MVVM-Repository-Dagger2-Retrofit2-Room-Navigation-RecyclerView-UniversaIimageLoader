package com.m3ikshizuka.worldnews.db

import androidx.room.*
import com.m3ikshizuka.worldnews.model.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getArticles(): List<Article>

    @Query("SELECT * FROM article WHERE id = :articleId")
    fun getArticleById(articleId: Int): Article?

    @Insert
    fun insert(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(articleList: List<Article>)

    @Insert
    fun insert(vararg articles: Article)

    @Delete
    fun delete(article: Article)
}