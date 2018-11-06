package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import net.nourepide.learning.cryptocurrencymonitor.entity.Cryptocurrency
import org.json.JSONObject
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    val data = MutableLiveData<List<Cryptocurrency>>()
    var isLoading = MutableLiveData<Boolean>()

    init {
        thread {
            initialization()
        }
    }

    fun initialization() {
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
