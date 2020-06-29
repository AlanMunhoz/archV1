package com.example.archv1.presentation.adapter.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<MODEL>(diffUtils: DiffUtil.ItemCallback<MODEL>) :
    ListAdapter<MODEL, BaseViewHolder<MODEL>>(diffUtils) {

    private val isShimmer: Boolean
        get() = currentList.isEmpty()

    abstract var shimmerCellsQuantity: Int

    abstract fun onCreateVH(parent: ViewGroup, viewType: Int): BaseViewHolder<MODEL>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateVH(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<MODEL>, position: Int) {
        holder.bindView(getItemPosition(position), isShimmer)
    }

    override fun getItemCount() = if (isShimmer) shimmerCellsQuantity else currentList.size

    private fun getItemPosition(position: Int) = if (isShimmer) null else currentList[position]

    override fun submitList(list: List<MODEL?>?) {
        if (isShimmer) {
            notifyDataSetChanged()
        }
        super.submitList(list)
    }
}
