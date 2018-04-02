package com.chadnedin.newsapp.Common

import com.chadnedin.newsapp.Interface.NewsService
import com.chadnedin.newsapp.Remote.RetrofitClient

/**
 * Created by chads on 4/1/2018.
 */

object Common{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY="32f2d72df6ca4c8e830af7a2d825e8ec"


    val newsService:NewsService
    get()=RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

}