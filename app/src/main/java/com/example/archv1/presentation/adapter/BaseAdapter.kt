package com.example.archv1.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var isShimmer = true
    private var dataList = ArrayList<T>()
    abstract var shimmerCellsQuantity: Int

    abstract fun onCreateVH(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateVH(parent, viewType)

    override fun getItemCount() = if (isShimmer) shimmerCellsQuantity else dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindView(getPosition(position), isShimmer)
    }

    private fun getPosition(position: Int) = if (isShimmer) null else dataList[position]

    fun setList(list: List<T>) {
        isShimmer = false
        dataList = ArrayList(list)
        notifyDataSetChanged()
    }

    fun addList(item: T) {
        isShimmer = false
        dataList.add(item)
        notifyDataSetChanged()
    }
}
