package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class MainDialogFragment : DialogFragment() {

    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }

    fun setArguments(key: String, value: String): MainDialogFragment {
        arguments = Bundle().apply { putString(key, value) }

        return this
    }

    fun setCancelableDialog(value: Boolean): MainDialogFragment {
        isCancelable = value

        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(context!!)
            .setTitle(arguments!!["TITLE"] as String)
            .setPositiveButton("Close", null)
            .create()!!
            .setOnClickPositive { viewModel.clearCryptocurrency() }
            .setOnCleanObserver { dismiss() }

    private fun AlertDialog.setOnClickPositive(block: () -> Unit): AlertDialog {
        setOnShowListener {
            getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { block() }
        }

        return this
    }

    private fun <T> T.setOnCleanObserver(block: () -> Unit) : T {
        viewModel.chosenCryptocurrency.observe(this@MainDialogFragment, Observer {
            if (it == null) block()
        })

        return this
    }
}
