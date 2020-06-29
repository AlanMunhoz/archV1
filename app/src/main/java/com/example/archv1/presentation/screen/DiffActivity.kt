package com.example.archv1.presentation.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.archv1.R
import com.example.archv1.data.util.toAlbumView
import com.example.archv1.databinding.ActivityDiffBinding
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.presentation.adapter.AlbumAdapterDiffUtils
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiffActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityDiffBinding
    private val viewModel: AlbumViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_diff)
        setContentView(viewBinding.root)

        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        viewModel.album.observe(this, Observer { responseResult ->
            when (responseResult) {
                is ResponseResult.Success -> {
                    showResult(responseResult.data)
                }
                is ResponseResult.Failure -> showError(responseResult.message)
            }
        })

        viewModel.albumList.observe(this, Observer { responseResult ->
            when (responseResult) {
                is ResponseResult.Success -> {
                    viewBinding.rvList.apply {
                        (adapter as AlbumAdapterDiffUtils).submitList(
                            responseResult.data.map {
                                it.toAlbumView()
                            }
                        )
                    }
                }
                is ResponseResult.Failure -> showError(responseResult.message)
            }
        })

        initLayout()
    }

    private fun showResult(result: Album) {
        viewBinding.rvList.apply { (adapter as AlbumAdapterDiffUtils).submitList(listOf(result.toAlbumView())) }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun initLayout() {
        viewBinding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AlbumAdapterDiffUtils { string ->
                Toast.makeText(context, "Card: $string", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.loadAlbums()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DiffActivity::class.java)
            context.startActivity(intent)
        }
    }
}
