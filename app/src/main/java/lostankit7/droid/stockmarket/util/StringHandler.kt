package lostankit7.droid.stockmarket.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class StringHandler {

    /**
     * use the class when you have normal string not dependent on resources
     * normally when you have some string from network call
     */
    data class NormalString(val string: String) : StringHandler()

    /**
     * use the class when you want to use string from resources
     * @param[stringId] - string id from strings.xml
     * @param[arguments] - arguments to pass if there is any in the string
     */
    class ResourceString(
        @StringRes val stringId: Int, vararg val arguments: Any,
    ) : StringHandler()

    @Composable
    fun asString() = run {
        when (this) {
            is NormalString -> string
            is ResourceString -> stringResource(stringId, *arguments)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is NormalString -> this.string
            is ResourceString -> String.format(
                context.resources.getString(stringId), arguments
            )
        }
    }
}