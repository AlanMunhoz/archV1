package com.example.archv1.presentation.adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.archv1.R
import com.facebook.shimmer.ShimmerFrameLayout

abstract class BaseViewHolder<MODEL>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val colorShimmer by lazy {
        ContextCompat.getColor(itemView.context, R.color.color_shimmer)
    }

    private val shimmerFrameLayout by lazy {
        itemView.findViewWithTag<ShimmerFrameLayout>(itemView.context.getString(R.string.shimmerViewTag))
    }

    private var shimmerViewList: List<View>? = null

    abstract fun bindItem(item: MODEL?)

    protected fun setShimmerViews(views: List<View>) {
        shimmerViewList = views
    }

    fun bindView(item: MODEL?, isShimmer: Boolean) {
        if (isShimmer) {
            startShimmer()
        } else {
            stopShimmer()
            bindItem(item)
        }
    }

    private fun startShimmer() {
        shimmerFrameLayout?.startShimmer()
        shimmerViewList?.forEach { it.setBackgroundColor(colorShimmer) }
    }

    private fun stopShimmer() {
        shimmerFrameLayout?.stopShimmer()
        shimmerFrameLayout?.setShimmer(null)
        shimmerViewList?.forEach { it.background = null }
    }

    companion object {
        fun inflate(parent: ViewGroup, layoutId: Int): View {
            return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }
    }
}
