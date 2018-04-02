package com.chadnedin.newsapp.Adapter.ViewHolder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chadnedin.newsapp.Interface.ItemClickListener
import com.chadnedin.newsapp.Model.WebSite
import com.chadnedin.newsapp.R


class ListSourceAdapter(private val context:Context,private val webSite:WebSite):RecyclerView.Adapter<ListSourceViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.source_news_layout,parent,false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return webSite.sources!!.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder?, position: Int) {
        holder!!.source_title.text = webSite.sources!![position].name

        holder.setItemClickListener(object : ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(context, "Will be implemented in the next tutorial", Toast.LENGTH_SHORT).show()
            }
        })

    }

}