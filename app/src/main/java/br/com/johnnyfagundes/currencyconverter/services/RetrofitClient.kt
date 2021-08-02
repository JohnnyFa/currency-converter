package br.com.johnnyfagundes.currencyconverter.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit

        private val baseUrl = "http://api.currencylayer.com/"

        fun getRetrofitListInstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            if (!::retrofit.isInitialized) {

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <S> createservice(serviceClass: Class<S>): S {
            return getRetrofitListInstance().create(serviceClass)
        }
    }

}