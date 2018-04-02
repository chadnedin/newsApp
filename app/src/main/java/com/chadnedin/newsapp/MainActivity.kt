package com.chadnedin.newsapp

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.ThemedSpinnerAdapter
import android.widget.Toast
import com.chadnedin.newsapp.Adapter.ViewHolder.ListSourceAdapter
import com.chadnedin.newsapp.Common.Common
import com.chadnedin.newsapp.Interface.NewsService
import com.chadnedin.newsapp.Model.WebSite
import com.chadnedin.newsapp.R.id.recycler_view_source_news
import com.chadnedin.newsapp.R.id.swipe_to_refresh
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog:AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init cache DB
        Paper.init(this)

        //Init Service
        mService = Common.newsService

        //Init View
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)

        }

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)

        loadWebSiteSource(false)
    }

    private fun loadWebSiteSource(isRefresh: Boolean) {

        if(!isRefresh)
        {
            val cache = Paper.book().read<String>( "cache")
            if(cache != null && !cache.isBlank() && cache != "null")
            {
                //Read cache
                val webSite = Gson().fromJson<WebSite>(cache,WebSite::class.java)
                adapter = ListSourceAdapter(baseContext,webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            }
            else
            {
                //Load website and write cache
                dialog.show()
                //Fetch new data
                mService.sources.enqueue(object:retrofit2.Callback<WebSite>{
                    override fun onFailure(call: Call<WebSite>?, t: Throwable?) {
                        Toast.makeText(baseContext, "Failed1", Toast.LENGTH_SHORT)
                                .show()
                    }

                    override fun onResponse(call: Call<WebSite>?, response: Response<WebSite>?) {
                       adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        //Save to cache
                        Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                        dialog.dismiss()
                    }
                })
            }
        }
        else
        {
            swipe_to_refresh.isRefreshing=true
            //Fetch new data
            mService.sources.enqueue(object :retrofit2.Callback<WebSite>{
                override fun onFailure(call: Call<WebSite>?, t: Throwable?) {
                    Toast.makeText(baseContext, "Failed2", Toast.LENGTH_SHORT)
                            .show()
                }

                override fun onResponse(call: Call<WebSite>?, response: Response<WebSite>?) {
                    adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    //Save to cache
                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                    swipe_to_refresh.isRefreshing=false
                }
            })
        }
    }
}
