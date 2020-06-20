package com.example.archv1.presentation.screen

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.archv1.R
import com.example.archv1.data.util.toAlbumView
import com.example.archv1.databinding.ActivityMainBinding
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.presentation.adapter.AlbumAdapter2
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: AlbumViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(viewBinding.root)

        viewBinding.viewModel = viewModel
        viewBinding.activity = this
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
                        (adapter as AlbumAdapter2).setList(responseResult.data.map { it.toAlbumView() })
                    }
                    viewBinding.textView2.text = HtmlCompat.fromHtml(
                        resources.getQuantityString(
                            R.plurals.album_download_list,
                            responseResult.data.size,
                            responseResult.data.size
                        ), HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
                is ResponseResult.Failure -> showError(responseResult.message)
            }
        })

        viewBinding.descriptionView.apply {
            setShowingLine(3)
            addShowMoreText("Continue")
            addShowLessText("Less")
            setShowMoreColor(Color.RED) // or other color
            setShowLessTextColor(Color.RED) // or other color
        }

        initLayout()
    }

    private fun showResult(result: Album) {
        viewBinding.textView1.text = getString(
            R.string.album_description,
            result.id,
            result.userId,
            result.title
        )
        viewBinding.rvList.apply { (adapter as AlbumAdapter2).addList(result.toAlbumView()) }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun goToLoginActivity() {
        LoginActivity.start(this)
    }

    private fun initLayout() {
        viewBinding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AlbumAdapter2 { string ->
                Toast.makeText(context, "Card: $string", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.loadAlbums()
    }
}
