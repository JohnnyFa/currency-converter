package br.com.johnnyfagundes.currencyconverter.services.repository

import android.content.ContentValues
import android.content.Context
import br.com.johnnyfagundes.currencyconverter.services.constants.DataBaseConstants
import br.com.johnnyfagundes.currencyconverter.services.model.CoinModel
import java.lang.Exception

class CoinRepository private constructor(context: Context) {
    private var mCurrencyDataBaseHelper: CurrencyDataBaseHelper = CurrencyDataBaseHelper(context)

    companion object {
        private lateinit var repository: CoinRepository

        fun getInstance(context: Context): CoinRepository {
            if (!::repository.isInitialized) {
                repository = CoinRepository(context)
            }
            return repository
        }
    }

    fun save(coin: CoinModel): Boolean {
        return try {
            val db = mCurrencyDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.CURRENCY.COLUMNS.CODE, coin.code)
            contentValues.put(DataBaseConstants.CURRENCY.COLUMNS.NAME, coin.name)

            db.insert(DataBaseConstants.CURRENCY.TABLE_NAME, null, contentValues)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAllCoins(): List<CoinModel> {
        val list: MutableList<CoinModel> = ArrayList()

        return try {
            val db = mCurrencyDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.CURRENCY.COLUMNS.CODE,
                DataBaseConstants.CURRENCY.COLUMNS.NAME,
            )

            val cursor = db.query(
                DataBaseConstants.CURRENCY.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val code =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.CURRENCY.COLUMNS.CODE))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.CURRENCY.COLUMNS.NAME))

                    val coin = CoinModel(0,code, name)
                    list.add(coin)
                }
            }
            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    fun getCoinByNameOrCode(current: String): List<CoinModel> {
        val list: MutableList<CoinModel> = ArrayList()

        return try {
            val db = mCurrencyDataBaseHelper.readableDatabase

            val cursor =
                db.rawQuery(
                    "select name, code from currency where name like '%$current%' OR code like '%$current%' ",
                    null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val code =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.CURRENCY.COLUMNS.CODE))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.CURRENCY.COLUMNS.NAME))

                    val coin = CoinModel(0, code, name)
                    list.add(coin)
                }
            }
            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }

}