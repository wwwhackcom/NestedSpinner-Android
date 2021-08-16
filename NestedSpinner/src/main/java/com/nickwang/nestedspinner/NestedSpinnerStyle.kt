package com.nickwang.nestedspinner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

/**
 * @author nickwang
 * Created 12/08/21
 */
class NestedSpinnerStyle(context: Context, typedArray: TypedArray) {

    @ColorInt
    var popupBackgroundColour: Int = 0

    var initExpanded: Boolean = false

    var groupItemHeight: Int = 0

    @ColorInt
    var groupBackgroundColour: Int = 0

    @ColorInt
    var groupTextColour: Int = 0
    var groupTextSize: Int = 0

    var subItemHeight: Int = 0

    @ColorInt
    var subBackgroundColour: Int = 0

    @ColorInt
    var subTextColour: Int = 0
    var subTextSize: Int = 0

    init {
        popupBackgroundColour = typedArray.getColor(
            R.styleable.NestedSpinnerView_popupBackgroundColour,
            ContextCompat.getColor(context, R.color.black)
        )
        initExpanded = typedArray.getBoolean(R.styleable.NestedSpinnerView_initExpanded, false)
        groupItemHeight = typedArray.getDimensionPixelSize(
            R.styleable.NestedSpinnerView_groupItemHeight,
            context.resources.getDimensionPixelSize(R.dimen.group_height)
        )
        groupBackgroundColour = typedArray.getColor(
            R.styleable.NestedSpinnerView_groupBackgroundColour,
            ContextCompat.getColor(context, R.color.background_dark)
        )
        groupTextColour =
            typedArray.getColor(R.styleable.NestedSpinnerView_groupTextColour, Color.WHITE)
        groupTextSize =
            typedArray.getDimensionPixelSize(
                R.styleable.NestedSpinnerView_groupTextSize,
                context.resources.getDimensionPixelSize(R.dimen.group_text_size)
            )
        subItemHeight =
            typedArray.getDimensionPixelSize(
                R.styleable.NestedSpinnerView_subItemHeight,
                context.resources.getDimensionPixelSize(R.dimen.sub_height)
            )
        subBackgroundColour = typedArray.getColor(
            R.styleable.NestedSpinnerView_subBackgroundColour,
            ContextCompat.getColor(context, R.color.background_dark)
        )
        subTextColour =
            typedArray.getColor(R.styleable.NestedSpinnerView_subTextColour, Color.WHITE)
        subTextSize =
            typedArray.getDimensionPixelSize(
                R.styleable.NestedSpinnerView_subTextSize,
                context.resources.getDimensionPixelSize(R.dimen.sub_text_size)
            )
    }

}