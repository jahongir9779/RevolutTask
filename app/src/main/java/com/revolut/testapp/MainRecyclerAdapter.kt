package com.revolut.testapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.revolut.testapp.viewgroups.Interaction
import kotlinx.android.synthetic.main.item_currency.view.*
import java.math.BigDecimal
import java.text.DecimalFormat


class MainRecyclerAdapter(
    private val interaction: Interaction,
    var baseRate: BigDecimal?,
    var currentInputVal: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<String, BigDecimal>>() {

        override fun areItemsTheSame(
            oldItem: Pair<String, BigDecimal>,
            newItem: Pair<String, BigDecimal>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<String, BigDecimal>,
            newItem: Pair<String, BigDecimal>
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BlogPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_currency,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogPostViewHolder -> {
                holder.bind(baseRate, differ.currentList[position], interaction, currentInputVal)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Pair<String, BigDecimal>>) {
        differ.submitList(list)
    }

    class BlogPostViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), Interaction {

        var decimalFormat = DecimalFormat("0.##")

        fun bind(
            base: BigDecimal?,
            item: Pair<String, BigDecimal>,
            interaction: Interaction,
            currentInputVal: String
        ) = with(itemView) {

            var currency = ExtendedCurrency.getCurrencyByISO(item.first)

            itemView.tv_title_short.text = item.first
            itemView.tv_title_long.text = currency?.name

            Glide.with(context)
                .load(currency?.flag)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.iv_flag)


            itemView.parentCl.setOnClickListener {
                itemView.edt_input.requestFocus()
                val imm: InputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(itemView.edt_input, InputMethodManager.SHOW_IMPLICIT)

            }

            var textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    if (!p0.isNullOrBlank() && p0[0].equals("0")) {
                        p0.replace(0, 0, "")
                    }

                    if (adapterPosition == 0) {
                        interaction.onUserInput(
                            item,
                            adapterPosition,
                            if (p0.isNullOrBlank()) null else p0.toString().toBigDecimal()
                        )
                    }

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            }


            itemView.edt_input.onFocusChangeListener =
                View.OnFocusChangeListener { view, b ->
                    if (b) {
                        itemView.edt_input.setSelection(itemView.edt_input.text.length)
                        interaction.onInputViewFocused(view, item, adapterPosition)
                        itemView.edt_input.addTextChangedListener(textWatcher)
                    } else {
                        itemView.edt_input.removeTextChangedListener(textWatcher)
                    }
                }


            if (adapterPosition != 0) {
               if (base.toString() != "0.0") {
                    itemView.edt_input.setText(
                        if (base == null) ""
                        else decimalFormat.format((base.multiply(item.second)))
                    )
                }
            } else {
                itemView.edt_input.setText(currentInputVal)
            }


        }


    }

}

