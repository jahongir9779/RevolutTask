//package com.revolut.testapp.viewgroups
//
//import android.graphics.Color
//import android.view.View
//import com.revolut.testapp.R
//import com.xwray.groupie.kotlinandroidextensions.Item
//import com.xwray.groupie.kotlinandroidextensions.ViewHolder
//import kotlinx.android.synthetic.main.item_currency.view.*
//
//
//class CurrencyItemView(
//    var base: Double,
//    var currency: String,
//    var rate: Double,
//    var interaction: Interaction
//) : Item() {
//
//
//    override fun bind(viewHolder: ViewHolder, position: Int) {
//        viewHolder.itemView.tv_title_short.text = currency
//        viewHolder.itemView.edt_input.setText(rate.toString())
//
//        viewHolder.itemView.edt_input.onFocusChangeListener =
//            View.OnFocusChangeListener { view, b ->
//                if (b) {
//                    viewHolder.itemView.tv_title_long.setBackgroundColor(Color.BLACK)
//                    interaction.onInputViewFocused(this, position)
//                }else{
//                    viewHolder.itemView.tv_title_long.setBackgroundColor(Color.WHITE)
//                }
//            }
//
//
//    }
//
//    override fun getLayout() = R.layout.item_currency
//
//
//}
//
