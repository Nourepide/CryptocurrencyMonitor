package net.nourepide.learning.cryptocurrencymonitor

import net.nourepide.learning.cryptocurrencymonitor.enity.Cryptocurrency
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

var data = arrayListOf<Cryptocurrency>()

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