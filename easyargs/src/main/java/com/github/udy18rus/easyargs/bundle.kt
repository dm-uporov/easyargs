package com.github.udy18rus.easyargs

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import java.io.Serializable

//<editor-fold desc="Fragment actions">
inline fun <reified T : Fragment> newInstance(): T = T::class.java.newInstance()

inline fun <reified T : Fragment> newInstance(vararg items: ArgumentWithKey<*>): T {
    if (items.asSequence()
            .map(ArgumentWithKey<*>::key)
            .toSet()
            .size < items.size
    ) throw IllegalArgumentException(
        "Use different keys for two or more items. " +
                "If you have two items of the same type, set explicit keys for each."
    )

    return newInstance<T>().apply {
        arguments = Bundle().apply {
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
    }
}

fun <T : Parcelable> Bundle.with(parcelable: T, key: String) = apply { putParcelable(key, parcelable) }

fun <T : Serializable> Bundle.with(serializable: T, key: String) = apply { putSerializable(key, serializable) }

inline fun <reified T> Fragment.getArg(byKey: String = T::class.java.simpleName): T {
    return checkOnExistence(getArgOrNull(byKey), byKey, Bundle::class)
}

inline fun <reified T> Fragment.getArgOrNull(byKey: String = T::class.java.simpleName): T? {
    return arguments?.getArgOrNull(byKey)
}

inline fun <reified T> Bundle.getArg(byKey: String = T::class.java.simpleName): T {
    return checkOnExistence(getArgOrNull(byKey), byKey, Bundle::class)
}

inline fun <reified T> Bundle.getArgOrNull(byKey: String = T::class.java.simpleName): T? {
    return when {
        T::class.isSerializable() -> getSerializable(byKey) as? T
        T::class.isParcelable() -> getParcelable<Parcelable>(byKey) as? T
        else -> throw IllegalArgumentException("${Bundle::class} cannot have arguments by type ${T::class}")
    }
}
//</editor-fold>