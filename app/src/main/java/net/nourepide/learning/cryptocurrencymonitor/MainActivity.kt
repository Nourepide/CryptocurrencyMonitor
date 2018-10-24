package net.nourepide.learning.cryptocurrencymonitor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import net.nourepide.learning.cryptocurrencymonitor.enity.Cryptocurrency
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter()

            thread {
                if (!isInitialized) {
                    val jsonArray = JSONObject(Utils.getDataURL()).getJSONArray("data")

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray[i] as JSONObject

                        data += Cryptocurrency(
                            jsonObject.getString("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("symbol")
                        )
                    }

                    isInitialized = true
                }

                runOnUiThread { reload() }
            }
        }
    }

    companion object {
        val data = arrayListOf<Cryptocurrency>()
        private var isInitialized = false
    }

    private fun RecyclerView.reload() {
        adapter!!.notifyDataSetChanged()
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.appearance))
    }
}
