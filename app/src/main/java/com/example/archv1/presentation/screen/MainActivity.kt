package com.example.archv1.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.archv1.R
import com.example.archv1.data.model.ResponseResult
import com.example.archv1.databinding.ActivityMainBinding
import com.example.archv1.presentation.viewModel.AlbumViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this).get(AlbumViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        viewModel.album2.observe(this, Observer { responseResult ->
            when(responseResult) {
                is ResponseResult.Success -> viewBinding.textView1.text = "${responseResult.data.title} (${viewModel.getCurrentPage()})"
                is ResponseResult.Failure -> Toast.makeText(this, responseResult.message, Toast.LENGTH_SHORT).show()
            }
        })

    }
}
