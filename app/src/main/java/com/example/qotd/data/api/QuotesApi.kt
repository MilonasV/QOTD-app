package com.example.qotd.data.api

import com.example.qotd.data.model.Quote
import retrofit2.http.GET

interface QuotesApi {
    @GET("quotes.json")
    suspend fun getQuotes(): List<Quote>


}