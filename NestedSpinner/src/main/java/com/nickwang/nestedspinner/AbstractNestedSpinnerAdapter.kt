package com.nickwang.nestedspinner

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * @author nickwang
 * Created 10/07/21
 */
abstract class AbstractNestedSpinnerAdapter<SO>(context: Context) :
    ExpandableListAdapter<AbstractNestedSpinnerAdapter<SO>.GroupItemViewHolder, AbstractNestedSpinnerAdapter<SO>.SubItemViewHolder>() {

    protected abstract fun getSubText(so: SO): String
    protected abstract fun getGroupBackgroundColour(groupItemIndex: Int): Int
    protected abstract fun getGroupTextColour(groupItemIndex: Int): Int
    protected abstract fun getSubBackgroundColour(so: SO): Int
    protected abstract fun getSubTextColour(so: SO): Int

    var style: NestedSpinnerStyle? = null
        set(value) {
            field = value
            setInitExpanded(style?.initExpanded ?: false)
        }

    lateinit var onGroupItemSelected: (groupItemIndex: Int) -> Unit
    lateinit var onSubItemSelected: (groupItemIndex: Int, subItemIndex: Int) -> Unit
    private val mContext: Context = context
    private var mDataSource: List<DataSource<String, SO>>? = null


    fun setDataSource(data: List<DataSource<String, SO>>) {
        mDataSource = data
        notifyNewData(mDataSource!!)
    }

    fun getSubItem(groupItemIndex: Int, subItemIndex: Int): SO {
        return mDataSource!![groupItemIndex].subItems[subItemIndex]
    }

    fun getSubItemText(groupItemIndex: Int, subItemIndex: Int): String {
        val subItem = getSubItem(groupItemIndex, subItemIndex)
        return getSubText(subItem)
    }

    fun getFlatSubItems(): List<String>? {
        if (mDataSource == null) return null

        var flatSubItems = ArrayList<String>()
        mDataSource!!.forEach { groupItem ->
            run {
                groupItem.subItems.forEach {
                    flatSubItems.add(getSubText(it))
                }
            }
        }
        return flatSubItems
    }

    override fun groupItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder? {
        val view: View = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_item_nested_group, parent, false)
        return GroupItemViewHolder(mContext, view)
    }

    override fun subItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder? {
        val view: View = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_item_nested_sub, parent, false)
        return SubItemViewHolder(view)
    }

    override fun onGroupItemBindViewHolder(holder: RecyclerView.ViewHolder?, groupItemIndex: Int) {
        (holder as AbstractNestedSpinnerAdapter<*>.GroupItemViewHolder).tvGroupItem.text =
            if (mDataSource != null && mDataSource!!.isNotEmpty()) mDataSource!![groupItemIndex].groupItem else ""
        val backgroundColour = getGroupBackgroundColour(groupItemIndex)
        setBackgroundColour(
            holder.rlGroup,
            if (backgroundColour != 0) backgroundColour else style?.groupBackgroundColour ?: 0
        )
        val textColour = getGroupTextColour(groupItemIndex)
        holder.tvGroupItem.setTextColor(
            if (textColour != 0) textColour else style?.groupTextColour ?: 0
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSubItemBindViewHolder(
        holder: RecyclerView.ViewHolder?,
        groupItemIndex: Int,
        subItemIndex: Int
    ) {
        if (mDataSource == null && mDataSource!!.isEmpty()) return
        val item = getSubText(mDataSource!![groupItemIndex].subItems[subItemIndex])
        val subItemViewHolder: SubItemViewHolder =
            holder as AbstractNestedSpinnerAdapter<SO>.SubItemViewHolder
        subItemViewHolder.tvSubItem.text = item
        val backgroundColour = getSubBackgroundColour(getSubItem(groupItemIndex, subItemIndex))
        setBackgroundColour(
            subItemViewHolder.rlSub,
            if (backgroundColour != 0) backgroundColour else style?.subBackgroundColour ?: 0
        )
        val textColour = getSubTextColour(getSubItem(groupItemIndex, subItemIndex))
        subItemViewHolder.tvSubItem.setTextColor(
            if (textColour != 0) textColour else style?.subTextColour ?: 0
        )
    }

    override fun onGroupItemClick(
        isExpand: Boolean?,
        holder: GroupItemViewHolder,
        groupItemIndex: Int
    ) {
        val groupItemViewHolder: GroupItemViewHolder = holder
        groupItemViewHolder.setArrowImage(
            mContext,
            if (isExpand == true) R.drawable.icon_right_arrow else R.drawable.icon_down_arrow
        )
        onGroupItemSelected.invoke(groupItemIndex)
    }

    override fun onSubItemClick(holder: SubItemViewHolder, groupItemIndex: Int, subItemIndex: Int) {
        onSubItemSelected.invoke(groupItemIndex, subItemIndex)
    }

    private fun setBackgroundColour(bg: ViewGroup, colour: Int) {
        bg.setBackgroundResource(R.drawable.list_item_nested_background)
        val drawable = bg.background as GradientDrawable
        drawable.setColor(colour)
    }

    inner class GroupItemViewHolder(context: Context, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var rlGroup: RelativeLayout = itemView.findViewById(R.id.rl_group)
        var tvGroupItem: TextView = itemView.findViewById(R.id.tv_group)
        var ivArrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        fun setArrowImage(context: Context, @DrawableRes id: Int) {
            val drawable = ContextCompat.getDrawable(context, id)
            drawable?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(context, R.color.text_light),
                BlendModeCompat.SRC_ATOP
            )
            ivArrow.setImageDrawable(drawable)
        }

        init {
            setArrowImage(
                context,
                if (isInitExpanded()) R.drawable.icon_down_arrow else R.drawable.icon_right_arrow
            )
            setGroupItemHeight()
            setTextSize()
        }

        private fun setGroupItemHeight() {
            val viewParams = rlGroup.layoutParams
            viewParams.height = style?.groupItemHeight
                ?: mContext.resources.getDimensionPixelSize(R.dimen.group_height)
            rlGroup.layoutParams = viewParams
        }

        private fun setTextSize() {
            tvGroupItem.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                (style?.groupTextSize
                    ?: mContext.resources.getDimensionPixelSize(R.dimen.group_text_size)).toFloat()
            )
        }
    }

    inner class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rlSub: RelativeLayout = itemView.findViewById(R.id.rl_sub)
        var tvSubItem: TextView = itemView.findViewById(R.id.tv_item)

        init {
            setSubItemHeight()
            setTextSize()
        }

        private fun setSubItemHeight() {
            val viewParams = rlSub.layoutParams
            viewParams.height =
                style?.subItemHeight ?: mContext.resources.getDimensionPixelSize(R.dimen.sub_height)
            rlSub.layoutParams = viewParams
        }

        private fun setTextSize() {
            tvSubItem.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                (style?.subTextSize
                    ?: mContext.resources.getDimensionPixelSize(R.dimen.sub_text_size)).toFloat()
            )
        }
    }

}