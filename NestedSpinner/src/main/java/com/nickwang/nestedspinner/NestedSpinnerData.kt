package com.nickwang.nestedspinner

import android.graphics.Color

/**
 * @author nickwang
 * Created 12/08/21
 */
open class NestedSpinnerData(
    var name: String,
    var data: Any,
    var backgroundColour: String,
    var textColour: String
) : NestedSpinnerDataSource {

    constructor(name: String) : this(name, "", "", "")

    constructor(name: String, data: Any) : this(name, data, "", "")

    override fun getSubText(): String {
        return name
    }

    override fun getGroupBackgroundColour(): Int {
        return 0
    }

    override fun getGroupTextColour(): Int {
        return 0
    }

    override fun getSubBackgroundColour(): Int {
        return NestedSpinnerUtils.getColour(backgroundColour, 0)
    }

    override fun getSubTextColour(): Int {
        return NestedSpinnerUtils.getColour(textColour, Color.WHITE)
    }

}