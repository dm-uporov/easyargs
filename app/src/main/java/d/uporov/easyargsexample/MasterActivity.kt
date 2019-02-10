package d.uporov.easyargsexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.udy18rus.easyargs.*
import kotlinx.android.synthetic.main.activity_master.*

class MasterActivity : AppCompatActivity() {

    private val receiver = Receiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        startActivityButton.setOnClickListener(::onStartActivityClicked)
        startFragmentButton.setOnClickListener(::onStartFragmentClicked)
        startServiceButton.setOnClickListener(::onStartServiceClicked)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter(TIMER_TICK_ACTION))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(receiver)
    }

    private fun onStartActivityClicked(v: View) {
        if (checkBoxWithArgument.isChecked) {
            start<DetailsActivity>(SomeModel().asArg())
        } else {
            start<DetailsActivity>()
        }
    }

    private fun onStartFragmentClicked(v: View) {
        val fragment: DetailsFragment = if (checkBoxWithArgument.isChecked) {
            newInstance(SomeModel().asArg())
        } else {
            newInstance()
        }

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onStartServiceClicked(v: View) {
        stopService<TimerService>()

        val timeString = timeForService.text.toString()
        val time = if (timeString.isEmpty()) 0 else timeString.toInt()
        startService<TimerService>(time.asArg())
    }

    inner class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val time = intent.getArg<Int>()
            currentTime.text = if (time > 0) "Current time is $time" else null
        }
    }
}