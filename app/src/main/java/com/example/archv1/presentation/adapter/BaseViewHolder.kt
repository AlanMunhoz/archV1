package com.example.archv1.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.archv1.R
import com.facebook.shimmer.ShimmerFrameLayout

abstract class BaseViewHolder<T>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val shimmerFrameLayout by lazy { itemView.findViewById<ShimmerFrameLayout>(R.id.flShimmer) }
    private var shimmerViewList: List<View>? = null

    abstract fun bindItem(item: T?)

    protected fun setShimmerViews(views: List<View>) {
        shimmerViewList = views
    }

    fun bindView(item: T?, isShimmer: Boolean) {
        if (isShimmer) {
            startShimmer()
        } else {
            stopShimmer()
            bindItem(item)
        }
    }

    private fun startShimmer() {
        shimmerFrameLayout?.startShimmer()
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
