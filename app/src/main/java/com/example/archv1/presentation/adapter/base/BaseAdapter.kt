package com.example.archv1.presentation.adapter.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<MODEL> : RecyclerView.Adapter<BaseViewHolder<MODEL>>() {

    private val isShimmer: Boolean
        get() = dataList.isEmpty()

    private var dataList = ArrayList<MODEL>()

    abstract var shimmerCellsQuantity: Int

    abstract fun onCreateVH(parent: ViewGroup, viewType: Int): BaseViewHolder<MODEL>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateVH(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<MODEL>, position: Int) {
        holder.bindView(getItem(position), isShimmer)
    }

    override fun getItemCount() = if (isShimmer) shimmerCellsQuantity else dataList.size

    private fun getItem(position: Int) = if (isShimmer) null else dataList[position]

    fun setList(list: List<MODEL>) {
        dataList = ArrayList(list)
        notifyDataSetChanged()
    }

    fun addList(item: MODEL) {
        dataList.add(item)
        notifyItemInserted(dataList.lastIndex)
    }
}
