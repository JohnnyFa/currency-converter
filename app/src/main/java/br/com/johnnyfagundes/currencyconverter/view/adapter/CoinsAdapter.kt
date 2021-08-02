package br.com.johnnyfagundes.currencyconverter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.johnnyfagundes.currencyconverter.R
import br.com.johnnyfagundes.currencyconverter.services.model.CoinModel
import br.com.johnnyfagundes.currencyconverter.view.viewholder.CoinViewHolder

class CoinsAdapter: RecyclerView.Adapter<CoinViewHolder>()  {

    private var mCoinList: List<CoinModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_row_coin, parent, false)
        return CoinViewHolder(item)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(mCoinList[position])
    }

    override fun getItemCount(): Int {
        return mCoinList.count()
    }

    fun updateCoins(list: List<CoinModel>) {
        mCoinList = list
        notifyDataSetChanged()
    }
}