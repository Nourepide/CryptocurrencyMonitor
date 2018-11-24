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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter(viewModel)
            viewModel.data.observe(this@MainActivity, Observer { adapter!!.notifyDataSetChanged() })
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.handleRefreshSwipe()
        }

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

            if (it != null && fragmentNotExist) {
                viewModel.clearCryptocurrency()

                MainDialogFragment()
                    .setArguments("TITLE", it.run { "$name : $symbol" })
                    .setCancelableDialog(false)
                    .show(supportFragmentManager, "mainDialogFragment")
            }
        })
    }
}
