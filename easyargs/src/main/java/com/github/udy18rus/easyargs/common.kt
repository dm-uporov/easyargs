package com.github.udy18rus.easyargs

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KClass


inline fun <reified T> checkOnExistence(item: T?, key: String, container: KClass<*>): T {
    return item ?: throw IllegalArgumentException("$container must has ${T::class} extra by key $key")
}

fun KClass<*>.isSerializable() = Serializable::class.java.isAssignableFrom(this.java)

fun KClass<*>.isParcelable() = Parcelable::class.java.isAssignableFrom(this.java)

fun Intent.renderArguments(vararg items: ArgumentWithKey<*>) = apply {
    for (item in items) {
        when (item.arg) {
            null -> Unit
            is Serializable -> this.with(item.arg, item.key)
            is Parcelable -> this.with(item.arg, item.key)
            else -> throw IllegalArgumentException(
                "Class ${item.arg::class} must implement ${Parcelable::class} or ${Serializable::class}"
            )
        }
    }
}

