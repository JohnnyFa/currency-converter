package br.com.johnnyfagundes.currencyconverter.services

import br.com.johnnyfagundes.currencyconverter.services.model.CurrencyModel
import br.com.johnnyfagundes.currencyconverter.services.model.CurrencyQuote
import br.com.johnnyfagundes.currencyconverter.services.model.QuoteModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Service {

    @GET("list?access_key=3411d3f0556e4ce8c936e87d2f541fb7")
    fun listCurrency(): Call<CurrencyModel>

    @GET("live?access_key=3411d3f0556e4ce8c936e87d2f541fb7")
    fun liveCurrency(): Call<CurrencyQuote>

}