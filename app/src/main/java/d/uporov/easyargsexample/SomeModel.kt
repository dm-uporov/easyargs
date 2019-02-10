package d.uporov.easyargsexample

import java.io.Serializable

data class SomeModel(
    val content: String = "",
    val type: Int = 0
) : Serializable