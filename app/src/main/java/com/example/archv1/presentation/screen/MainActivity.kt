package com.example.archv1.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.archv1.R
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

        //viewBinding.button.setOnClickListener {
            //viewModel.getSingleAlbumResponse(viewBinding.editText.text.toString().toInt()).observe(this, Observer { response ->
                //mActivityMainBinding.textView.text = response.body()?.title
            //})
        //}

        viewModel.getIsUpdating().isUpdating.observe(this, Observer {  isUpdating ->
            viewBinding.progressBar.visibility = if(isUpdating) View.VISIBLE else View.GONE
        })

        viewModel.getIsUpdating().messageResult.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })

    }
}
