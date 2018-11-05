package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import android.view.View
import net.nourepide.learning.cryptocurrencymonitor.entity.Cryptocurrency
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private var isInitialized = false
    val data = arrayListOf<Cryptocurrency>()
    val isVisible = ObservableInt(View.VISIBLE)

    fun initialization() {
        when (isInitialized) {
            false -> isInitialized = true
            true -> return
        }

        val jsonArray = JSONObject(Utils.getDataURL()).getJSONArray("data")

        (0 until jsonArray.length())
            .map { jsonArray[it] as JSONObject }
            .forEach {
                data += Cryptocurrency(
                    it.getString("id"),
                    it.getString("name"),
                    it.getString("symbol")
                )
            }

        isVisible.set(View.GONE)
    }
}
