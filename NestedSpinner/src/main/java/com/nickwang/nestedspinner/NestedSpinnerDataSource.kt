package com.nickwang.nestedspinner

/**
 * @author nickwang
 * Created 10/07/21
 */
interface NestedSpinnerDataSource {

    fun getSubText(): String
    fun getGroupBackgroundColour(): Int
    fun getGroupFontColour(): Int
    fun getSubBackgroundColour(): Int
    fun getSubFontColour(): Int

}