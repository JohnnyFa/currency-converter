package br.com.johnnyfagundes.currencyconverter.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.johnnyfagundes.currencyconverter.R
import br.com.johnnyfagundes.currencyconverter.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        observer()
    }

    override fun onClick(v: View?) {
        val intent: Intent?
        when (v?.id) {
            R.id.check_coins -> {
                intent = Intent(this, CoinActivity::class.java)
                startActivity(intent)
            }
            R.id.convert_coins -> {
                intent = Intent(this, CurrencyConverterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun observer() {
        mViewModel.currencyList.observe(this, Observer {
            if (it.isEmpty()) {
                mViewModel.save()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load()
    }
}