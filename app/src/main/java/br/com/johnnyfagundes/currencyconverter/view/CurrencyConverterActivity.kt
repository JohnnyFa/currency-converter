package br.com.johnnyfagundes.currencyconverter.view

import android.R
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.johnnyfagundes.currencyconverter.databinding.ActivityCurrencyConverterBinding
import br.com.johnnyfagundes.currencyconverter.services.model.QuoteModel
import br.com.johnnyfagundes.currencyconverter.viewmodel.CurrencyViewModel
import br.com.johnnyfagundes.currencyconverter.viewmodel.QuoteViewModel
import java.text.DecimalFormat

class CurrencyConverterActivity : AppCompatActivity() {

    lateinit var binding: ActivityCurrencyConverterBinding
    private lateinit var mViewModel: QuoteViewModel
    private lateinit var mCurrencyViewModel: CurrencyViewModel
    private var liveCurrency = arrayListOf<QuoteModel>()
    private val usd = "USD"
    private val brl = "BRL"
    private var value = 0
    private var originCoin: String = ""
    private var destinyCoin: String = ""
    private var firstCurrency: Double = 0.0
    private var secondCurrency: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(QuoteViewModel::class.java)
        mCurrencyViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)

        mCurrencyViewModel.load()

        binding.spinnerOrigin.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    originCoin = parent?.getItemAtPosition(position).toString()
                }
            }

        binding.spinnerDestination.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    destinyCoin = parent?.getItemAtPosition(position).toString()
                }
            }
        observer()
    }

    fun onClickConverter(v: View) {
        if (binding.edittextOrigin.text.isNotEmpty()) {
            val format = DecimalFormat("#.##")
            liveCurrency = arrayListOf()
            mViewModel.loadLiveQuote()
            var origem = usd + originCoin
            origem = origem.replace("\\s".toRegex(), "")

            var destiny = usd + destinyCoin
            destiny = destiny.replace("\\s".toRegex(), "")

            if (originCoin.trim() != usd && destinyCoin.trim() != usd) {
                for (i in liveCurrency.indices) {
                    val currency = liveCurrency[i]
                    if (currency.quote.trim() == origem) {
                        firstCurrency = currency.value.trim().toDouble()
                        println(firstCurrency)
                    }
                    if (currency.quote.trim() == destiny) {
                        secondCurrency = currency.value.trim().toDouble()
                        println(secondCurrency)
                    }
                }

                val qntd = binding.edittextOrigin.text.toString()
                firstCurrency = 1 / firstCurrency
                secondCurrency = 1 / secondCurrency

                var final = firstCurrency / secondCurrency
                final *= qntd.toDouble()
                val textFinal = if (destinyCoin.trim() != "BTC") {
                    "Conversão: \$ ${format.format(final)}"
                } else {
                    "Conversão: \$ $final"
                }
                binding.textviewResult.text = textFinal
                print(final)
            }
            if (originCoin.trim() == usd) {
                for (i in liveCurrency.indices) {
                    val currency = liveCurrency[i]
                    if (currency.quote.trim() == destiny) {
                        var textFinal: String = ""
                        val qntd = binding.edittextOrigin.text.toString()
                        val final = currency.value.toDouble() * qntd.toDouble()
                        textFinal = if (destinyCoin.trim() != "BTC") {
                            "Conversão: \$ ${format.format(final)}"
                        } else {
                            "Conversão: \$ $final"
                        }
                        binding.textviewResult.text = textFinal
                    }
                }
            }

            if (destinyCoin.trim() == usd) {
                for (i in liveCurrency.indices) {
                    val currency = liveCurrency[i]
                    if (currency.quote.trim() == origem) {
                        var textFinal: String = ""
                        val qntd = binding.edittextOrigin.text.toString()
                        val format = DecimalFormat("#.##")
                        val realValue = 1 / currency.value.toDouble()
                        val final = realValue * qntd.toDouble()
                        textFinal = if (destinyCoin.trim() != "BTC") {
                            "Conversão: \$ ${format.format(final)}"
                        } else {
                            "Conversão: \$ $final"
                        }
                        binding.textviewResult.text = textFinal
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val context: Context = applicationContext
        val isOnline = isOnline(context)
        if (isOnline) {
            mViewModel.delete()
            mViewModel.saveLivePrice()
        }
    }

    private fun observer() {
        mCurrencyViewModel.currencyList.observe(this, Observer {
            if (it.isNotEmpty()) {
                val myList: ArrayList<String> = arrayListOf()
                for (i in it.indices) {
                    myList.add(it[i].code)
                }
                println(it.size)
                println("adding results into spinners")
                val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, myList)
                binding.spinnerOrigin.adapter = adapter
                binding.spinnerDestination.adapter = adapter
            }
        })

        mViewModel.priceList.observe(this, Observer {
            if (it.isNotEmpty()) {
                for (i in it.indices) {
                    liveCurrency.add(it[i])
                }
            }
        })

        mViewModel.deleted.observe(this, Observer {
            print("success -> $it")
        })

    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
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