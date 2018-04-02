package com.chadnedin.newsapp.Model



class WebSite{
    var status:String?=null
    var sources:List<Source>?=null

    constructor(sources: List<Source>?) {
        this.sources = sources
    }


}