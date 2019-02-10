package com.github.udy18rus.easyargs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import java.io.Serializable


//<editor-fold desc="Activity actions">
inline fun <reified T : AppCompatActivity> Context.start(
    intentSettings: Intent.() -> Unit = {}
) {
    createIntent<T>(intentSettings).also(::startActivity)
}

inline fun <reified T : AppCompatActivity> Fragment.start(
    intentSettings: Intent.() -> Unit = {}
) {
    context?.start<T>(intentSettings)
}

inline fun <reified T : AppCompatActivity> Activity.startForResult(
    requestCode: Int,
    intentSettings: Intent.() -> Unit = {}
) {
    createIntent<T>(intentSettings)
        .also { startActivityForResult(it, requestCode) }
}

inline fun <reified T : AppCompatActivity> Fragment.startForResult(
    requestCode: Int,
    intentSettings: Intent.() -> Unit = {}
) {
    activity?.startForResult<T>(requestCode, intentSettings)
}

inline fun <reified T : AppCompatActivity> Context.start(
    vararg items: ArgumentWithKey<*>,
    intentSettings: Intent.() -> Unit = {}
) {
    createIntent<T>(items = *items, intentSettings = intentSettings)
        .also(::startActivity)
}

inline fun <reified T : AppCompatActivity> Fragment.start(
    vararg items: ArgumentWithKey<*>,
    intentSettings: Intent.() -> Unit = {}
) {
    context?.start<T>(items = *items, intentSettings = intentSettings)
}

inline fun <reified T : AppCompatActivity> Activity.startForResult(
    requestCode: Int,
    vararg items: ArgumentWithKey<*>,
    intentSettings: Intent.() -> Unit = {}
) {
    createIntent<T>(items = *items, intentSettings = intentSettings)
        .also { startActivityForResult(it, requestCode) }
}

inline fun <reified T : AppCompatActivity> Fragment.startForResult(
    requestCode: Int,
    vararg items: ArgumentWithKey<*>,
    intentSettings: Intent.() -> Unit = {}
) {
    activity?.createIntent<T>(items = *items, intentSettings = intentSettings)
        .also { startActivityForResult(it, requestCode) }
}

inline fun <reified T : AppCompatActivity> Context.createIntent(
    vararg items: ArgumentWithKey<*>,
    intentSettings: Intent.() -> Unit = {}
): Intent =
    createIntent<T>(intentSettings).apply {
        for (item in items) {
            when (item.arg) {
                null -> Unit
                is Serializable -> this.with(item.arg, item.key)
                is Parcelable -> this.with(item.arg, item.key)
                else -> throw IllegalArgumentException(
                    "Class ${item.arg::class} must implement " +
                            Parcelable::class + " or " + Serializable::class
                )
            }
        }
    }

inline fun <reified T : AppCompatActivity> Context.createIntent(intentSettings: Intent.() -> Unit = {}) =
    Intent(this, T::class.java).apply(intentSettings)

fun <T : Parcelable> Intent.with(parcelable: T, key: String): Intent = putExtra(key, parcelable)

fun <T : Serializable> Intent.with(serializable: T, key: String): Intent = putExtra(key, serializable)

inline fun <reified T> AppCompatActivity.getArg(byKey: String = T::class.java.simpleName): T {
    return checkOnExistence(getArgOrNull(byKey), byKey, Intent::class)
}

inline fun <reified T> AppCompatActivity.getArgOrNull(byKey: String = T::class.java.simpleName): T? {
    return intent.getArgOrNull(byKey)
}

inline fun <reified T> Intent.getArg(byKey: String = T::class.java.simpleName): T {
    return checkOnExistence(getArgOrNull(byKey), byKey, Intent::class)
}

inline fun <reified T> Intent.getArgOrNull(byKey: String = T::class.java.simpleName): T? {
    return when {
        T::class.isParcelable() -> getParcelableExtra<Parcelable>(byKey) as? T
        T::class.isSerializable() -> getSerializableExtra(byKey) as? T
        else -> throw IllegalArgumentException("${Intent::class} cannot have arguments by type ${T::class}")
    }
}
//</editor-fold>