package com.nickwang.nestedspinner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author nickwang
 * Created 10/07/21
 */
class NestedSpinnerPopupView(context: Context, style: NestedSpinnerStyle) : PopupWindow(context) {

    private val mContext: Context = context
    private var mAdapter: ExpandableListAdapter<*, *>? = null
    private var mListView: RecyclerView? = null
    private val mStyle: NestedSpinnerStyle = style

    init {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.popup_nested_list, null)
        contentView = view
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mListView = view.findViewById(R.id.nested_list)
        mListView?.layoutManager = LinearLayoutManager(mContext)
        val bg: LinearLayout = view.findViewById(R.id.ll_bg)
        bg.setBackgroundResource(R.drawable.nested_spinner_background)
        val drawable = bg.background as GradientDrawable
        drawable.setColor(mStyle.popupBackgroundColour)
    }

    fun setAdapter(adapter: ExpandableListAdapter<*, *>?) {
        mAdapter = adapter
        mListView!!.adapter = mAdapter
    }

}