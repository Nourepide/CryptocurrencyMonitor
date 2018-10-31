package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModel
import net.nourepide.learning.cryptocurrencymonitor.enity.Cryptocurrency

class MainViewModel : ViewModel() {
    val data = arrayListOf<Cryptocurrency>()
    var isInitialized = false
}