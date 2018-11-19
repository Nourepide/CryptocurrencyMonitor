package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModel
import net.nourepide.learning.cryptocurrencymonitor.Utils.Companion.mutableLiveData
import net.nourepide.learning.cryptocurrencymonitor.entity.Cryptocurrency
import org.json.JSONObject
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    val data = mutableLiveData(listOf<Cryptocurrency>())
    val isLoading = mutableLiveData(false)
    val chosenCryptocurrency = mutableLiveData<Cryptocurrency>()

    init {
        reload()
    }

    private fun reload() {
        if (isLoading.value == true) return else isLoading.value = true

        thread {
            val jsonArray = JSONObject(Utils.getDataURL()).getJSONArray("data")

            (0 until jsonArray.length())
                .map { jsonArray[it] as JSONObject }
                .map {
                    Cryptocurrency(
                        it.getString("id"),
                        it.getString("name"),
                        it.getString("symbol")
                    )
                }.toList().apply { data.postValue(this) }

            isLoading.postValue(false)
        }
    }

    fun handleRefreshSwipe() {
        reload()
    }

    fun handleClick(cryptocurrency: Cryptocurrency) {
        chosenCryptocurrency.value = cryptocurrency
    }
}
