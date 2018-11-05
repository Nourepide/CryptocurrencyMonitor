package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import net.nourepide.learning.cryptocurrencymonitor.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        thread {
            viewModel.initialization()
            runOnUiThread { reload() }
        }
    }

    @MainThread
    private fun reload() {
        fun disableProgressBar() {
            binding.progressBar.visibility = View.GONE
        }

        fun setDataRecyclerView() {
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MainListAdapter(viewModel).apply { notifyDataSetChanged() }
                startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appearance))
            }
        }

        disableProgressBar()
        setDataRecyclerView()
    }
}
