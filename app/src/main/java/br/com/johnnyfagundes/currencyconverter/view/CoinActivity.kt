package br.com.johnnyfagundes.currencyconverter.view

import android.R
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.johnnyfagundes.currencyconverter.databinding.ActivityCoinsBinding
import br.com.johnnyfagundes.currencyconverter.view.adapter.CoinsAdapter
import br.com.johnnyfagundes.currencyconverter.viewmodel.CoinViewModel

class CoinActivity : AppCompatActivity() {

    private val mAdapter: CoinsAdapter = CoinsAdapter()
    private lateinit var curencies: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context: Context = applicationContext
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        curencies =
            ViewModelProvider(this).get(CoinViewModel::class.java)

        binding.recyclerAllCoins.layoutManager = LinearLayoutManager(context)
        binding.recyclerAllCoins.adapter = mAdapter

        binding.edittextSearchCoin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                curencies.getTypedCoin(binding.edittextSearchCoin.text.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                curencies.getTypedCoin(binding.edittextSearchCoin.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })

        curencies.load()

        observer()
    }

    private fun observer() {
        curencies.coinList.observe(this, Observer {
            mAdapter.updateCoins(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}