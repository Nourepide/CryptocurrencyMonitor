package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.MutableLiveData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Utils {
    companion object {
        fun getDataURL() = getDataURL("https://api.coinmarketcap.com/v2/listings/")

        fun getDataURL(url: String): String {
            lateinit var buffer: StringBuffer
            lateinit var reader: BufferedReader

            (URL(url).openConnection() as HttpURLConnection).apply {
                try {
                    connect()
                    buffer = StringBuffer()
                    reader = BufferedReader(InputStreamReader(inputStream))

                    reader.forEachLine { buffer.appendln(it) }
                } catch (e: Exception) {
                    // null
                    return ""
                } finally {
                    reader.close()
                    disconnect()
                }
            }

            return buffer.toString()
        }

        fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
            val data = MutableLiveData<T>()

            if (defaultValue != null) {
                data.value = defaultValue
            }

            return data
        }
    }
}
