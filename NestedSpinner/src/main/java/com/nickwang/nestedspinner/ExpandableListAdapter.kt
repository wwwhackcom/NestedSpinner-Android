package com.nickwang.nestedspinner

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author nickwang
 * Created 10/07/21
 */
@Suppress("TooManyFunctions")
abstract class ExpandableListAdapter<GVH, SVH : RecyclerView.ViewHolder?> :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var initExpanded: Boolean = false
    private val groupItemStatus: MutableList<Boolean> = ArrayList()
    private var dataTrees: List<DataSource<*, *>> = ArrayList()
    fun notifyNewData(data: List<DataSource<*, *>>) {
        setDataTrees(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataTrees(dt: List<DataSource<*, *>>) {
        dataTrees = dt
        initGroupItemStatus(groupItemStatus)
        notifyDataSetChanged()
    }

    private fun initGroupItemStatus(l: MutableList<Boolean>?) {
        for (i in dataTrees.indices) {
            l!!.add(initExpanded)
        }
    }

    abstract fun groupItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder?
    abstract fun subItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        if (viewType == ItemStatus.VIEW_TYPE_GROUP_ITEM) {
            viewHolder = groupItemViewHolder(parent)
        } else if (viewType == ItemStatus.VIEW_TYPE_SUB_ITEM) {
            viewHolder = subItemViewHolder(parent)
        }
        return viewHolder!!
    }

    abstract fun onGroupItemBindViewHolder(holder: RecyclerView.ViewHolder?, groupItemIndex: Int)
    abstract fun onSubItemBindViewHolder(
        holder: RecyclerView.ViewHolder?,
        groupItemIndex: Int,
        subItemIndex: Int
    )

    abstract fun onGroupItemClick(isExpand: Boolean?, holder: GVH, groupItemIndex: Int)
    abstract fun onSubItemClick(holder: SVH, groupItemIndex: Int, subItemIndex: Int)

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemStatus = getItemStatusByPosition(position)
        val dt = dataTrees[itemStatus.groupItemIndex]
        if (itemStatus.viewType == ItemStatus.VIEW_TYPE_GROUP_ITEM) {
            onGroupItemBindViewHolder(holder, itemStatus.groupItemIndex)
            holder.itemView.setOnClickListener {
                val groupItemIndex = itemStatus.groupItemIndex
                if (!groupItemStatus[groupItemIndex]) {
                    onGroupItemClick(false, holder as GVH, groupItemIndex)
                    groupItemStatus[groupItemIndex] = true
                    notifyItemRangeInserted(holder.bindingAdapterPosition + 1, dt.subItems.size)
                } else {
                    onGroupItemClick(true, holder as GVH, groupItemIndex)
                    groupItemStatus[groupItemIndex] = false
                    notifyItemRangeRemoved(holder.bindingAdapterPosition + 1, dt.subItems.size)
                }
            }
        } else if (itemStatus.viewType == ItemStatus.VIEW_TYPE_SUB_ITEM) {
            onSubItemBindViewHolder(
                holder, itemStatus.groupItemIndex, itemStatus
                    .subItemIndex
            )
            holder.itemView.setOnClickListener {
                onSubItemClick(
                    holder as SVH,
                    itemStatus.groupItemIndex,
                    itemStatus.subItemIndex
                )
            }
        }
    }

    override fun getItemCount(): Int {
        var itemCount = 0
        if (groupItemStatus.size == 0) {
            return 0
        }
        for (i in dataTrees.indices) {
            if (groupItemStatus[i]) {
                itemCount += dataTrees[i].subItems.size + 1
            } else {
                itemCount++
            }
        }
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return getItemStatusByPosition(position).viewType
    }

    fun getGroupItemStatus(position: Int): Boolean {
        return groupItemStatus[position]
    }

    fun isInitExpanded(): Boolean {
        return initExpanded
    }

    fun setInitExpanded(initExpanded: Boolean) {
        this.initExpanded = initExpanded
    }

    @Suppress("LoopWithTooManyJumpStatements")
    private fun getItemStatusByPosition(position: Int): ItemStatus {
        val itemStatus = ItemStatus()
        var count = 0
        var i = 0
        while (i < groupItemStatus.size) {
            if (count == position) {
                itemStatus.viewType = ItemStatus.VIEW_TYPE_GROUP_ITEM
                itemStatus.groupItemIndex = i
                break
            } else if (count > position) {
                itemStatus.viewType = ItemStatus.VIEW_TYPE_SUB_ITEM
                itemStatus.groupItemIndex = i - 1
                itemStatus.subItemIndex = position - (count - dataTrees[i - 1].subItems.size)
                break
            }
            count++
            if (groupItemStatus[i]) {
                count += dataTrees[i].subItems.size
            }
            i++
        }
        if (i >= groupItemStatus.size) {
            itemStatus.groupItemIndex = i - 1
            itemStatus.viewType = ItemStatus.VIEW_TYPE_SUB_ITEM
            itemStatus.subItemIndex = position - (count - dataTrees[i - 1].subItems.size)
        }
        return itemStatus
    }

    protected class ItemStatus {
        var viewType = 0
        var groupItemIndex = 0
        var subItemIndex = -1

        companion object {
            const val VIEW_TYPE_GROUP_ITEM = 0
            const val VIEW_TYPE_SUB_ITEM = 1
        }
    }

    class DataSource<K, V>(val groupItem: K, val subItems: List<V>)
}
