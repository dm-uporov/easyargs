package d.uporov.easyargs

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable

/**
 *  Wrapping is needed to restrict type T by factory methods. Allowed only Parcelable and Serializable types
 */
data class ArgumentWithKey<T> internal constructor(val arg: T, val key: String)

fun <T : Parcelable> T.asArg(key: String = this::class.java.simpleName) = ArgumentWithKey(this, key)

fun <T : Serializable> T.asArg(key: String = this::class.java.simpleName) = ArgumentWithKey(this, key)

/**
 * This method is needed for classes which implements Parcelable and Serializable together.
 * "T : Any?" is mr.kostyl' ordered to method has not conflicts with two others "asArg".
 *  It's used when model is Parcelable and Serializable at the same time.
 */
fun <T> T.asArg(key: String = this::class.java.simpleName) where T : Serializable, T : Parcelable, T : Any? =
    (this as Parcelable).asArg(key)

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("Serializable's or Parcelable's child allowed only.", level = DeprecationLevel.ERROR)
inline fun <reified T : Any> T.asArg(key: String = this::class.java.simpleName): Nothing =
    throw IllegalArgumentException()

/**
 * This method allows to wrap an argument into Intent (as result Intent for example)
 */
fun <T> ArgumentWithKey<T>.asIntent() = Intent().apply {
    when (arg) {
        is Serializable -> putExtra(key, arg)
        is Parcelable -> putExtra(key, arg)
    }
}