package com.revolut.testapp

//import com.revolut.data.CurrencyRatesDataRepository
//import com.revolut.domain.interactor.SingleUseCase
//import com.revolut.domain.interactor.browse.GetCurrencyRates
//import com.revolut.domain.model.CurrencyRates
//import com.revolut.domain.repository.CurrencyRatesRepository
//import com.revolut.testapp.remote.CurrencyRatesService
//import com.revolut.remote.CurrencyRatesServiceFactory
//import com.revolut.domain.interactor.browse.GetCurrencyRates
//import com.revolut.domain.model.CurrencyRates
//import com.revolut.domain.repository.CurrencyRatesRepository
//import com.revolut.testapp.remote.CurrencyRatesService
import androidx.lifecycle.MutableLiveData
import com.revolut.testapp.base.BaseViewModel
import com.revolut.testapp.data.CurrencyRatesModel
import com.revolut.testapp.remote.CurrencyRatesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Wasabeef on 2018/03/02.
 */
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    val isLoading = MutableLiveData<APICALLSTATUS>(APICALLSTATUS.NONE)


    @Inject
    lateinit var service: CurrencyRatesService


    lateinit var response: CurrencyRatesModel

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    var message = "No connection!"


    fun getCurrencies() {
        disposable.add(service.getCurrencyRates().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { isLoading.value = APICALLSTATUS.LOADING }
            .subscribe(
                { resp ->
                    response = resp

//                    var map = HashMap<String, Double>()
//                    map.put("ONE", 1.0)
//                    map.put("TWO", 2.0)
//                    map.put("Three", 3.0)
//response = CurrencyRatesModel(map, "EUR")
                    isLoading.value = APICALLSTATUS.SUCCESS
                },
                {
                    message = "Something went wrong: " + it.message
                    isLoading.value = APICALLSTATUS.ERROR
                })
        )

    }


}