package com.example.archv1.presentation.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.archv1.R
import com.example.archv1.presentation.model.AlbumView

class AlbumAdapter2(private val cardClickCallback: (String) -> Unit) : BaseAdapter<AlbumView>() {

    override var shimmerCellsQuantity: Int = 7

    override fun onCreateVH(parent: ViewGroup, viewType: Int): BaseViewHolder<AlbumView> {
        return AlbumViewHolder(parent, cardClickCallback)
    }
}

class AlbumViewHolder(parent: ViewGroup, private val cardClickCallback: (String) -> Unit) :
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
