package com.github.udy18rus.easyargs

import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KClass


inline fun <reified T> checkOnExistence(item: T?, key: String, container: KClass<*>): T {
    return item ?: throw IllegalArgumentException("$container must has ${T::class} extra by key $key")
}

fun KClass<*>.isSerializable() = Serializable::class.java.isAssignableFrom(this.java)

fun KClass<*>.isParcelable() = Parcelable::class.java.isAssignableFrom(this.java)



