package com.example.archv1.presentation.adapter

// class AlbumAdapter(
//    private val cardClickCallback: (String) -> Unit
// ) : RecyclerView.Adapter<ItemViewHolder>() {
//
//    private var albumList = ArrayList<AlbumView>()
//    private var isShimmer = albumList.isEmpty()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val viewBinding: AlbumItemBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(parent.context),
//            R.layout.album_item,
//            parent,
//            false
//        )
//        val colorShimmer = ContextCompat.getColor(viewBinding.root.context, R.color.color_shimmer)
//        return ItemViewHolder(viewBinding, colorShimmer)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        Log.d("18062020", "Pos: $position Shimmer: $isShimmer")
//        holder.bindView(getPosition(position), isShimmer, cardClickCallback)
//    }
//
//    override fun getItemCount() = if (isShimmer) SHIMMER_CELLS else albumList.size
//
//    private fun getPosition(position: Int) = if (isShimmer) null else albumList[position]
//
//    fun setList(albumViewList: List<AlbumView>) {
//        isShimmer = false
//        albumList = ArrayList(albumViewList)
//        notifyDataSetChanged()
//    }
//
//    fun addList(albumView: AlbumView) {
//        isShimmer = false
//        albumList.add(albumView)
//        notifyDataSetChanged()
//    }
//
//    companion object {
//        private const val SHIMMER_CELLS = 10
//    }
// }
//
// class ItemViewHolder(private val viewBinding: AlbumItemBinding, private val colorShimmer: Int) :
//    RecyclerView.ViewHolder(viewBinding.root) {
//
//    fun bindView(entity: AlbumView?, isShimmer: Boolean, cardClickCallback: (String) -> Unit) {
//        with(viewBinding) {
//            if (isShimmer) {
//                startShimmer()
//            } else {
//                stopShimmer()
//                entity?.let { albumView ->
//                    tvTitle.text = albumView.title
//                    tvId.text = albumView.id
//                    clParentCard.setOnClickListener { cardClickCallback(albumView.title) }
//                }
//            }
//        }
//    }
//
//    private fun startShimmer() {
//        with(viewBinding) {
//            // flShimmer.startShimmer()
//            tvTitle.setBackgroundColor(colorShimmer)
//            tvId.setBackgroundColor(colorShimmer)
//            ivAlbum.setBackgroundColor(colorShimmer)
//        }
//    }
//
//    private fun stopShimmer() {
//        with(viewBinding) {
//            // flShimmer.stopShimmer()
//            // flShimmer.setShimmer(null)
//            tvTitle.background = null
//            tvId.background = null
//            ivAlbum.background = null
//        }
//    }
// }
