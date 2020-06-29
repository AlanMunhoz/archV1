package com.example.archv1.presentation.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.example.archv1.R
import com.example.archv1.presentation.adapter.base.BaseListAdapter
import com.example.archv1.presentation.adapter.base.BaseViewHolder
import com.example.archv1.presentation.model.AlbumView

class AlbumAdapterDiffUtils(private val cardClickCallback: (String) -> Unit) :
    BaseListAdapter<AlbumView>(DiffCallback()) {

    override var shimmerCellsQuantity: Int = 7

    override fun onCreateVH(parent: ViewGroup, viewType: Int): BaseViewHolder<AlbumView> {
        return AlbumViewHolderDiffUtils(parent, cardClickCallback)
    }
}

class AlbumViewHolderDiffUtils(parent: ViewGroup, private val cardClickCallback: (String) -> Unit) :
    BaseViewHolder<AlbumView>(inflate(parent, R.layout.album_item)) {

    private val albumImage by lazy { itemView.findViewById<ImageView>(R.id.ivAlbum) }
    private val albumTitle by lazy { itemView.findViewById<TextView>(R.id.tvTitle) }
    private val albumId by lazy { itemView.findViewById<TextView>(R.id.tvId) }

    init {
        setShimmerViews(listOf(albumImage, albumTitle, albumId))
    }

    override fun bindItem(item: AlbumView?) {
        item?.let { albumView ->
            albumTitle.text = albumView.title
            albumId.text = albumView.id
            itemView.setOnClickListener { cardClickCallback(albumView.title) }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<AlbumView>() {
    override fun areItemsTheSame(oldItem: AlbumView, newItem: AlbumView) = oldItem == newItem
    override fun areContentsTheSame(oldItem: AlbumView, newItem: AlbumView) =
        oldItem.id == newItem.id
}
