package com.revolut.testapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revolut.testapp.viewgroups.Interaction
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var myRecyclerAdapter: MainRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        rv.layoutManager = LinearLayoutManager(this)
        mainViewModel.getCurrencies()
        rv.setHasFixedSize(true)

        mainViewModel.isLoading.observe(this, Observer { apiCallStatus ->
            when (apiCallStatus) {
                APICALLSTATUS.LOADING -> {
//                    progress.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                    tv_message.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true

                }
                APICALLSTATUS.SUCCESS -> {
//                    progress.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                    rv.visibility = View.VISIBLE
                    tv_message.visibility = View.GONE

                    val currencyRatesList =
                        ArrayList<Pair<String, BigDecimal>>(mainViewModel.response.rates.toList())
                    currencyRatesList.add(
                        0,
                        Pair(mainViewModel.response.baseCurrency, BigDecimal.valueOf(1.0))
                    )

                    myRecyclerAdapter = MainRecyclerAdapter(object : Interaction {
                        override fun onInputViewFocused(
                            view: View,
                            item: Pair<String, BigDecimal>,
                            position: Int
                        ) {
                            if (position == 0) return
                            currencyRatesList.removeAt(position)
                            currencyRatesList.add(0, item)
                            myRecyclerAdapter.submitList(currencyRatesList)
                            myRecyclerAdapter.notifyItemMoved(position, 0)
                            (rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                                0,
                                20
                            )
                            Handler().postDelayed({
                                (view as EditText).setSelection(view.text.length)
                            }, 0)
                        }

                        override fun onUserInput(
                            item: Pair<String, BigDecimal>,
                            position: Int,
                            userInput: BigDecimal?
                        ) {
                            myRecyclerAdapter.currentInputVal = userInput?.toString() ?: ""
                            if (item.first == mainViewModel.response.baseCurrency) {
                                myRecyclerAdapter.baseRate =
                                    userInput
                            } else {
                                myRecyclerAdapter.baseRate = if (userInput == null) userInput else
                                    userInput.divide(
                                        mainViewModel.response.rates[item.first]!!,
                                        2,
                                        RoundingMode.HALF_UP
                                    )

                            }

                            myRecyclerAdapter.notifyDataSetChanged()

                        }
                    }, BigDecimal.valueOf(0.0), "")


                    rv.adapter = myRecyclerAdapter

                    myRecyclerAdapter.submitList(currencyRatesList)
                }
                APICALLSTATUS.ERROR -> {
//                    progress.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                    rv.visibility = View.GONE
                    tv_message.visibility = View.VISIBLE
                    tv_message.text = mainViewModel.message
                }
            }
        })


        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val view = currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        })



        swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.getCurrencies()
        }

    }


}

//
//EUR   = 10
//
//
//USD   = 2



