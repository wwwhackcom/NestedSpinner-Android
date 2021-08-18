package com.nickwang.nestedspinner

import android.content.Context

/**
 * @author nickwang
 * Created 10/07/21
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class BaseNestedSpinnerAdapter<T : NestedSpinnerDataSource>(context: Context) :
    AbstractNestedSpinnerAdapter<T>(context) {

    constructor(context: Context, data: List<DataSource<String, T>>) : this(context) {
        setDataSource(data)
    }

    override fun getSubText(t: T): String {
        val subItem = t as NestedSpinnerDataSource
        return subItem.getSubText()
    }

    override fun getGroupBackgroundColour(groupItemIndex: Int): Int {
        val subItem = getSubItem(groupItemIndex, 0)
        return subItem.getGroupBackgroundColour()
    }

    override fun getGroupTextColour(groupItemIndex: Int): Int {
        val subItem = getSubItem(groupItemIndex, 0)
        return subItem.getGroupTextColour()
    }

    override fun getSubBackgroundColour(t: T): Int {
        val subItem = t as NestedSpinnerDataSource
        return subItem.getSubBackgroundColour()
    }

    override fun getSubTextColour(t: T): Int {
        val subItem = t as NestedSpinnerDataSource
        return subItem.getSubTextColour()
    }
}
