package lostankit7.droid.stockmarket.util

import android.content.Context
import androidx.annotation.StringRes

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
            @StringRes val stringId: Int, vararg val arguments: Any
        ) : StringHandler()

        /** method to called from UI layer to get the string*/
        fun getString(context: Context): String {
            return when (this) {
                is NormalString -> this.string
                is ResourceString -> String.format(
                    context.resources.getString(stringId), arguments
                )
            }
        }
    }