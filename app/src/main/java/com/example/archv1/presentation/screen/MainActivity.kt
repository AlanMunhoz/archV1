package com.example.archv1.presentation.screen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.archv1.R
import com.example.archv1.data.util.toAlbumView
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.databinding.ActivityMainBinding
import com.example.archv1.domain.model.Album
import com.example.archv1.presentation.adapter.AlbumAdapter
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
                is ResponseResult.Success -> { showResult(responseResult.data) }
                is ResponseResult.Failure -> showError(responseResult.message)
            }
        })

        viewModel.albumList.observe(this, Observer { responseResult ->
            when (responseResult) {
                is ResponseResult.Success -> {
                    viewBinding.rvList.apply {
                        (adapter as AlbumAdapter).setList(responseResult.data.map{ it.toAlbumView() })
                    }
                }
                is ResponseResult.Failure -> showError(responseResult.message)
            }
        })

        viewBinding.descriptionView.setShowingLine(3)
        viewBinding.descriptionView.addShowMoreText("Continue")
        viewBinding.descriptionView.addShowLessText("Less")
        viewBinding.descriptionView.setShowMoreColor(Color.RED) // or other color
        viewBinding.descriptionView.setShowLessTextColor(Color.RED) // or other color

        initLayout()
    }

    private fun showResult(result: Album) {
        viewBinding.textView1.text = "${result.title} (${result.id})"
        viewBinding.rvList.apply { (adapter as AlbumAdapter).addList(result.toAlbumView()) }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun goToLoginActivity() {
        startActivity(Intent(this, TextFieldsActivity::class.java))
    }

    private fun initLayout() {
        viewBinding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AlbumAdapter(ArrayList()) { string ->
                Toast.makeText(context, "Card: $string", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.loadAlbums()
    }
}
