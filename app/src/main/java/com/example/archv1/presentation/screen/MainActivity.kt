package com.example.archv1.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.archv1.R
import com.example.archv1.presentation.viewModel.AlbumViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewmodel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel = ViewModelProvider(this).get(AlbumViewModel::class.java)

        button.setOnClickListener {
            viewmodel.getSingleAlbumResponse(editText.text.toString().toInt()).observe(this, Observer { response ->
                textView.text = response.body()?.title
            })
        }

        viewmodel.getIsUpdating().isUpdating.observe(this, Observer {  isUpdating ->
            progressBar.visibility = if(isUpdating) View.VISIBLE else View.GONE
        })

        viewmodel.getIsUpdating().messageResult.observe(this, Observer {  message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })

    }
}
