package com.nickwang.nestedspinner

import android.graphics.Color

/**
 * @author nickwang
 * Created 10/07/21
 */
@Suppress("TooGenericExceptionCaught")
object NestedSpinnerUtils {
    @JvmStatic
    fun getColour(colourString: String, colourDefault: Int): Int {
        return try {
            Color.parseColor(colourString)
        } catch (e: Exception) {
            println(e)
            colourDefault
        }
    }
}
