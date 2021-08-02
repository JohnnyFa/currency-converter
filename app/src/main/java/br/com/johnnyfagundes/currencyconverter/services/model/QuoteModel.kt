package br.com.johnnyfagundes.currencyconverter.services.model

data class QuoteModel(
    var id: Int = 0,
    var quote: String,
    var value: String,
)