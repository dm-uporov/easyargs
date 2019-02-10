package com.github.udy18rus.easyargs

import android.app.Service
import android.content.Context
import android.content.Intent


//<editor-fold desc="Service actions">
inline fun <reified T : Service> Context.startService(
    vararg items: ArgumentWithKey<*>,
    intentSettings: Intent.() -> Unit = {}
) {
    Intent(this, T::class.java)
        .apply(intentSettings)
        .renderArguments(*items)
        .run(::startService)
}

inline fun <reified T : Service> Context.stopService() {
    stopService(Intent(this, T::class.java))
}
//</editor-fold>