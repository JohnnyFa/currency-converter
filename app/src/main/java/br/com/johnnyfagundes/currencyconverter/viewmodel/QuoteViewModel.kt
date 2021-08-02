package br.com.johnnyfagundes.currencyconverter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.johnnyfagundes.currencyconverter.services.RetrofitClient
import br.com.johnnyfagundes.currencyconverter.services.Service
import br.com.johnnyfagundes.currencyconverter.services.model.CurrencyQuote
import br.com.johnnyfagundes.currencyconverter.services.model.QuoteModel
import br.com.johnnyfagundes.currencyconverter.services.repository.QuoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuoteViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private var mSaveCoins = MutableLiveData<Boolean>()
    val remote = RetrofitClient.createservice(Service::class.java)
    val call: Call<CurrencyQuote> = remote.liveCurrency()

    private var liveCurrency = arrayListOf<QuoteModel>()
    val coinLiveDataPrice = MutableLiveData<List<QuoteModel>>()
    val priceList: LiveData<List<QuoteModel>> = coinLiveDataPrice
    private val mQuotesRepository: QuoteRepository = QuoteRepository.getInstance(mContext)
    val isDeleted = MutableLiveData<Boolean>()
    var deleted: LiveData<Boolean> = isDeleted

    fun saveLivePrice() {
        val response = call.enqueue(object : Callback<CurrencyQuote> {
            override fun onResponse(call: Call<CurrencyQuote>, res: Response<CurrencyQuote>) {
                if (res.body()?.quotes != null) {
                    val coins = res.body()!!.quotes
                    val keyName = coins.map { coins.keys.toString() }
                    val valuesName = coins.map { coins.values.toString() }

                    var key: String = keyName[0]
                    key = key.replace("[", "")
                    key = key.replace("]", "")
                    val keys = key.split(",")

                    var value: String = valuesName[0]
                    value = value.replace("[", "")
                    value = value.replace("]", "")
                    val values = value.split(",")

                    for (i in keys.indices) {
                        val quote = QuoteModel(0, keys[i], values[i])
                        liveCurrency.add(quote)
                        mSaveCoins.value = mQuotesRepository.save(quote)
//                    println("inserido -> ${quote.quote}")
//                    println("message -> ${mSaveCoins.value}")
                    }
                    coinLiveDataPrice.value = liveCurrency
                    println("Success - inserted data")
                }

            }

            override fun onFailure(call: Call<CurrencyQuote>, t: Throwable) {
                Log.i("onCall - saveLivePrice()", "")
            }
        })
    }

    fun loadLiveQuote() {
        coinLiveDataPrice.value = mQuotesRepository.getAllQuotes()
    }
    fun delete() {
        isDeleted.value = mQuotesRepository.delete()
    }
}