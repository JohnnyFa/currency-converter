package br.com.johnnyfagundes.currencyconverter.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.johnnyfagundes.currencyconverter.R
import br.com.johnnyfagundes.currencyconverter.services.model.CoinModel

class CoinViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(coin: CoinModel){
        val textCoinName = itemView.findViewById<TextView>(R.id.textview_currencyName)
        textCoinName.text = coin.name

        val textCoin = itemView.findViewById<TextView>(R.id.textview_currency)
        textCoin.text = coin.code
    }
}