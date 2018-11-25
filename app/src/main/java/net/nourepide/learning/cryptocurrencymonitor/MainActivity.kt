package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils.loadAnimation
import net.nourepide.learning.cryptocurrencymonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val adapter by lazy { MainListAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.handleRefreshSwipe()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainActivity.adapter
        }

        viewModel.data.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(this, Observer {
            when (it) {
                true -> binding.swipeRefresh.isRefreshing = it
                false -> binding.apply {
                    binding.swipeRefresh.isRefreshing = it
                    recyclerView.animation = loadAnimation(applicationContext, R.anim.appearance)
                }
            }
        })

        viewModel.chosenCryptocurrency.observe(this, Observer {
            val fragmentNotExist = supportFragmentManager.findFragmentByTag("mainDialogFragment") == null

            if (it != null && fragmentNotExist) MainDialogFragment()
                .setArguments("TITLE", it.run { "$name : $symbol" })
                .setCancelableDialog(false)
                .show(supportFragmentManager, "mainDialogFragment")
        })
    }
}
