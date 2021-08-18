package com.nickwang.nestedspinner

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import java.io.Serializable

/**
 * @author nickwang
 * Created 10/07/21
 */
class NestedSpinnerView : AppCompatSpinner, OnTouchListener, Serializable {
    lateinit var onItemSelectedListener: (subItem: Any?) -> Unit
    private var mAbstractAdapter: AbstractNestedSpinnerAdapter<*>? = null
    private var mNestedPopupView: NestedSpinnerPopupView? = null
    private lateinit var mStyle: NestedSpinnerStyle

    constructor(context: Context) : super(context) {
        init(context, null, R.attr.NestedSpinnerViewStyle)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, R.attr.NestedSpinnerViewStyle)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.NestedSpinnerView, defStyleAttr, 0).also {
            mStyle = NestedSpinnerStyle(context, it)
        }.recycle()
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            showPopup()
        }
        return true
    }

    fun setNestedAdapter(abstractNestedAdapter: AbstractNestedSpinnerAdapter<*>?) {
        mAbstractAdapter = abstractNestedAdapter
        mAbstractAdapter?.onGroupItemSelected = { _ -> run {} }
        mAbstractAdapter?.onSubItemSelected = { groupItemIndex, subItemIndex ->
            run {
                val subItem: Any =
                    mAbstractAdapter!!.getSubItem(groupItemIndex, subItemIndex) ?: return@run

                val subItemText: String =
                    mAbstractAdapter!!.getSubItemText(groupItemIndex, subItemIndex)

                val index = getItemIndex(subItemText)
                if (index >= 0) {
                    setSelection(index)
                    dismissPopup()
                    onItemSelectedListener.invoke(subItem)
                }
            }
        }
        mAbstractAdapter?.style = mStyle
        initAdapter()
    }

    private fun showPopup() {
        mNestedPopupView = NestedSpinnerPopupView(context, mStyle)
        mNestedPopupView!!.setAdapter(mAbstractAdapter)
        mNestedPopupView!!.width = width
        val offsetX =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_X, resources.displayMetrics)
                .toInt()
        val offsetY =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_Y, resources.displayMetrics)
                .toInt()
        mNestedPopupView!!.showAsDropDown(this, offsetX, offsetY)
    }

    private fun dismissPopup() {
        mNestedPopupView?.dismiss()
    }

    private fun initAdapter() {
        val list = ArrayList<String>()
        list.add(context.getString(R.string.selectText))
        val subItems = mAbstractAdapter?.getFlatSubItems()
        if (subItems != null) {
            for (sub in subItems) {
                list.add(sub)
            }
        }
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
    }

    private fun getItemIndex(subItemText: String): Int {
        val adapter = adapter ?: return -1
        var index = -1
        for (i in 0 until adapter.count) {
            val o = adapter.getItem(i) as String
            if (o == subItemText) {
                index = i
            }
        }
        return index
    }

    companion object {
        private const val serialVersionUID = -1802140099958720779L
        private const val OFFSET_X = 9f
        private const val OFFSET_Y = 6f
    }
}
