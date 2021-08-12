package com.nickwang.nestedspinner.example

import android.graphics.Color
import com.nickwang.nestedspinner.NestedSpinnerDataSource
import com.nickwang.nestedspinner.NestedSpinnerUtils

/**
 * @author nickwang
 * Created 10/07/21
 */
data class Entity(var name: String, var data: Any, var backgroundColour: String, var textColour: String) : NestedSpinnerDataSource {

    override fun getSubText(): String {
        return name
    }

    override fun getGroupBackgroundColour(): Int {
        return Color.DKGRAY
    }

    override fun getGroupFontColour(): Int {
        return Color.WHITE
    }

    override fun getSubBackgroundColour(): Int {
        return NestedSpinnerUtils.getColour(backgroundColour, R.color.background_dark)
    }

    override fun getSubFontColour(): Int {
        return NestedSpinnerUtils.getColour(textColour, Color.WHITE)
    }
}
