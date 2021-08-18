package com.nickwang.nestedspinner

import androidx.annotation.ColorInt

/**
 * @author nickwang
 * Created 10/07/21
 */
interface NestedSpinnerDataSource {

    fun getSubText(): String

    @ColorInt
    fun getGroupBackgroundColour(): Int

    @ColorInt
    fun getGroupTextColour(): Int

    @ColorInt
    fun getSubBackgroundColour(): Int

    @ColorInt
    fun getSubTextColour(): Int
}
