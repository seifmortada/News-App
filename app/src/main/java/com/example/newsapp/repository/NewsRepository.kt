package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInterface
import com.example.newsapp.database.ArticlesDao
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import retrofit2.Response
import java.util.Locale.IsoCountryCode

class NewsRepository(val dao: ArticlesDao) {
    //API Functions
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInterface.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchWord: String, pageNumber: Int) =
        RetrofitInterface.api.getSearchNews(searchWord, pageNumber)

    //Room Database Functions
    suspend fun insertIntoDataBase(article: Article) =
        dao.insertOrUpdateArticle(article)

    suspend fun deleteArticle(article: Article) =
        dao.deleteArticle(article)

    fun getAllArticles() = dao.getAllArticles()

}