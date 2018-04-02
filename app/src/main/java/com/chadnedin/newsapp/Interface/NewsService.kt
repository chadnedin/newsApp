package com.chadnedin.newsapp.Interface

import com.chadnedin.newsapp.Model.WebSite
import retrofit2.http.GET
import retrofit2.Call

interface NewsService {

    @get:GET("v2/sources?apiKey=32f2d72df6ca4c8e830af7a2d825e8ec")
    val sources:Call<WebSite>
}