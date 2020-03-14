package com.revolut.testapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.util.*


/**
 * Representation for a [CurrencyRatesModel] fetched from the API
 */
@Parcelize
data class CurrencyRatesModel(
    var rates: HashMap<String, BigDecimal>,
    var baseCurrency: String
) : Parcelable