package net.nourepide.learning.cryptocurrencymonitor

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class MainDialogFragment : DialogFragment() {

    init {
        isCancelable = false
    }

    fun setArguments(key: String, value: String): MainDialogFragment {
        arguments = Bundle().apply { putString(key, value) }

        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(context!!)
        .setTitle(arguments!!["TITLE"] as String)
        .setPositiveButton("Close", null)
        .show()!!
}