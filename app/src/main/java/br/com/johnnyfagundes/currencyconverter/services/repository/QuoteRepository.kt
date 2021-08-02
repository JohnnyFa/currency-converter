package br.com.johnnyfagundes.currencyconverter.services.repository

import android.content.ContentValues
import android.content.Context
import br.com.johnnyfagundes.currencyconverter.services.constants.DataBaseConstants
import br.com.johnnyfagundes.currencyconverter.services.model.CoinModel
import br.com.johnnyfagundes.currencyconverter.services.model.QuoteModel
import java.lang.Exception

class QuoteRepository private constructor(context: Context) {
    private var mCurrencyDataBaseHelper: CurrencyDataBaseHelper = CurrencyDataBaseHelper(context)

    companion object {
        private lateinit var repository: QuoteRepository

        fun getInstance(context: Context): QuoteRepository {
            if (!::repository.isInitialized) {
                repository = QuoteRepository(context)
            }
            return repository
        }
    }

    fun save(quote: QuoteModel): Boolean {
        return try {
            val db = mCurrencyDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.QUOTES.COLUMNS.QUOTE, quote.quote)
            contentValues.put(DataBaseConstants.QUOTES.COLUMNS.VALUE, quote.value)

            db.insert(DataBaseConstants.QUOTES.TABLE_NAME, null, contentValues)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun getQuote(current: String): QuoteModel? {
        var list: QuoteModel? = null

        return try {
            val db = mCurrencyDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.QUOTES.COLUMNS.QUOTE,
                DataBaseConstants.QUOTES.COLUMNS.VALUE,
            )

            val selection = DataBaseConstants.QUOTES.COLUMNS.QUOTE + " LIKE ?"
            val args = arrayOf(current)

            val cursor = db.query(
                DataBaseConstants.QUOTES.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null,
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val quote =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.QUOTES.COLUMNS.QUOTE))
                val value =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.QUOTES.COLUMNS.VALUE))

                list = QuoteModel(0, quote, value)
            }
            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    fun getAllQuotes(): List<QuoteModel> {
        val list: MutableList<QuoteModel> = ArrayList()

        return try {
            val db = mCurrencyDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.QUOTES.COLUMNS.QUOTE,
                DataBaseConstants.QUOTES.COLUMNS.VALUE,
            )

            val cursor = db.query(
                DataBaseConstants.QUOTES.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val quote =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.QUOTES.COLUMNS.QUOTE))
                    val value =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.QUOTES.COLUMNS.VALUE))

                    val coin = QuoteModel(0,quote, value)
                    list.add(coin)
                }
            }
            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    fun delete(): Boolean {

        return try {
            val db = mCurrencyDataBaseHelper.writableDatabase

            db.execSQL("delete from "+ DataBaseConstants.QUOTES.TABLE_NAME);

            true
        } catch (e: Exception) {
            false
        }
    }
}