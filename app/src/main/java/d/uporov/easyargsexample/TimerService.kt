package d.uporov.easyargsexample

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import com.github.udy18rus.easyargs.asArg
import com.github.udy18rus.easyargs.asIntent
import com.github.udy18rus.easyargs.getArgOrNull

const val TIMER_TICK_ACTION = "TimerTick"

class TimerService : Service() {

    private val handler = Handler()
    private var nextCallback: Runnable? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        LocalBroadcastManager.getInstance(this)
            .tick(intent.getArgOrNull<Int>() ?: 5)
        return START_NOT_STICKY
    }

    private fun LocalBroadcastManager.tick(time: Int) {
        if (time < 0) return stopSelf()
        sendBroadcast(time.asArg().asIntent {
            action = TIMER_TICK_ACTION
        })
        nextCallback = Runnable { tick(time - 1) }
        handler.postDelayed(nextCallback, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        nextCallback?.let(handler::removeCallbacks)

    }
}