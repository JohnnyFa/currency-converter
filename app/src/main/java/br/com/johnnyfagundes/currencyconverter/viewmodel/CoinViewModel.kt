package br.com.johnnyfagundes.currencyconverter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.johnnyfagundes.currencyconverter.services.model.CoinModel
import br.com.johnnyfagundes.currencyconverter.services.repository.CoinRepository

class CoinViewModel (application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext

    private val mCoinsRepository: CoinRepository = CoinRepository.getInstance(mContext)

    val coinLiveData = MutableLiveData<List<CoinModel>>()

    val coinList: LiveData<List<CoinModel>> = coinLiveData

    fun load(){
        coinLiveData.value = mCoinsRepository.getAllCoins()
    }

    fun getTypedCoin(coin: String){
        coinLiveData.value = mCoinsRepository.getCoinByNameOrCode(coin)
    }
}