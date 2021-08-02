package br.com.johnnyfagundes.currencyconverter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.johnnyfagundes.currencyconverter.services.RetrofitClient
import br.com.johnnyfagundes.currencyconverter.services.Service
import br.com.johnnyfagundes.currencyconverter.services.model.CurrencyModel
import br.com.johnnyfagundes.currencyconverter.services.model.CoinModel
import br.com.johnnyfagundes.currencyconverter.services.model.CurrencyQuote
import br.com.johnnyfagundes.currencyconverter.services.model.QuoteModel
import br.com.johnnyfagundes.currencyconverter.services.repository.CoinRepository
import br.com.johnnyfagundes.currencyconverter.services.repository.QuoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mSaveCoins = MutableLiveData<Boolean>()

    val remote = RetrofitClient.createservice(Service::class.java)
    val call: Call<CurrencyModel> = remote.listCurrency()

    val coinLiveData = MutableLiveData<List<CoinModel>>()
    private val mCoinsRepository: CoinRepository = CoinRepository.getInstance(mContext)
    val currencyList: LiveData<List<CoinModel>> = coinLiveData
    private var listCurrency = arrayListOf<CoinModel>()

    fun save() {
        val response = call.enqueue(object : Callback<CurrencyModel> {
            override fun onResponse(call: Call<CurrencyModel>, res: Response<CurrencyModel>) {
                val coins = res.body()!!.currencies
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
                    val coin = CoinModel(0, keys[i], values[i])
                    listCurrency.add(coin)
                    mSaveCoins.value = mCoinsRepository.save(coin)
                }
                coinLiveData.value = listCurrency

                println("Success - inserted data")
            }

            override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {
                Log.i("onCall - save()", "")
            }
        })
    }

    fun load() {
        coinLiveData.value = mCoinsRepository.getAllCoins()
    }
}