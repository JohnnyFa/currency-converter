package br.com.johnnyfagundes.currencyconverter.services.model

import com.google.gson.annotations.SerializedName
data class CurrencyModel(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("terms")
    val terms: String,

    @SerializedName("privacy")
    val privacy: String,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("source")
    val source: String,

    @SerializedName("currencies")
    val currencies: Map<String, String>,

//    @SerializedName("quotes")
//    val quotes: Map<String, String>,

)