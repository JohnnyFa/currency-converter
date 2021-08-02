package br.com.johnnyfagundes.currencyconverter.services.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.johnnyfagundes.currencyconverter.services.constants.DataBaseConstants

class CurrencyDataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CURRENCY)
        db.execSQL(CREATE_TABLE_QUOTES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "coins.db"

        private const val CREATE_TABLE_CURRENCY =
            ("create table " + DataBaseConstants.CURRENCY.TABLE_NAME + " ("
                    + DataBaseConstants.CURRENCY.COLUMNS.ID + " integer primary key autoincrement, "
                    + DataBaseConstants.CURRENCY.COLUMNS.CODE + " text, "
                    + DataBaseConstants.CURRENCY.COLUMNS.NAME + " text);")

        private const val CREATE_TABLE_QUOTES =
            ("create table " + DataBaseConstants.QUOTES.TABLE_NAME + " ("
                    + DataBaseConstants.QUOTES.COLUMNS.ID + " integer primary key autoincrement, "
                    + DataBaseConstants.QUOTES.COLUMNS.QUOTE + " text, "
                    + DataBaseConstants.QUOTES.COLUMNS.VALUE + " text);")

    }
}
