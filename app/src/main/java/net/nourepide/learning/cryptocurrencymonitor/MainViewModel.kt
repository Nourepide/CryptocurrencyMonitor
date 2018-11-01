package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModel
import net.nourepide.learning.cryptocurrencymonitor.entity.Cryptocurrency
import org.json.JSONObject

class MainViewModel : ViewModel() {
    val data = arrayListOf<Cryptocurrency>()
    private var isInitialized = false

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
    }
}
