package br.com.johnnyfagundes.currencyconverter.services.constants

class DataBaseConstants private constructor() {

    object CURRENCY {
        const val TABLE_NAME = "currency"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val CODE = "code"
        }
    }

    object QUOTES {
        const val TABLE_NAME = "quote"
        object COLUMNS {
            const val ID = "id"
            const val QUOTE = "quote"
            const val VALUE = "value"
        }
    }
}
