package com.revolut.testapp.viewgroups

import android.view.View
import java.math.BigDecimal

interface Interaction {

    fun onInputViewFocused(view: View, item: Pair<String, BigDecimal>, position: Int){}
    fun onUserInput(
        item: Pair<String, BigDecimal>,
        position: Int,
        userInput: BigDecimal?
    ){}



}