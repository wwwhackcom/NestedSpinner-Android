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
    lateinit var mOnItemSelectedListener: (subItem: Any?) -> Unit

    private var mContext: Context
    private var mAbstractAdapter: AbstractNestedSpinnerAdapter<*>? = null
    private var mNestedPopupView: NestedSpinnerPopupView? = null

    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        init()
    }

    private fun init() {
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
                    mOnItemSelectedListener.invoke(subItem)
                }
            }
        }
        initAdapter()
    }

    private fun showPopup() {
        mNestedPopupView = NestedSpinnerPopupView(mContext)
        mNestedPopupView!!.setAdapter(mAbstractAdapter)
        mNestedPopupView!!.width = width
        val offsetX =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f, resources.displayMetrics)
                .toInt()
        val offsetY =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, resources.displayMetrics)
                .toInt()
        mNestedPopupView!!.showAsDropDown(this, offsetX, offsetY)
    }

    private fun dismissPopup() {
        mNestedPopupView?.dismiss()
    }

    private fun initAdapter() {
        var list = ArrayList<String>()
        list.add(mContext.getString(R.string.selectText))
        val subItems = mAbstractAdapter?.getFlatSubItems()
        if (subItems != null) {
            for (sub in subItems) {
                list.add(sub)
            }
        }
        adapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list)
    }

    private fun getItemIndex(subItemText: String): Int {
        val adapter = adapter ?: return -1
        for (i in 0 until adapter.count) {
            val o = adapter.getItem(i) as String
            if (o == subItemText) {
                return i
            }
        }
        return -1
    }

    companion object {
        private const val serialVersionUID = -1802140099958720779L
    }
}