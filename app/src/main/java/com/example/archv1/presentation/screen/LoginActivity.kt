package com.example.archv1.presentation.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.archv1.R
import kotlinx.android.synthetic.main.activity_text_fields.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_fields)

        textFieldName.setEndIconOnClickListener {
            Toast.makeText(this, "Type your name here!", Toast.LENGTH_LONG).show()
        }

        textFieldPass.editText?.doOnTextChanged { text, start, count, after ->
            textFieldPass.error = null
        }

        login_button_view.setOnClickListener {
            etPass.text?.toString()?.length?.let {
                if (it < 6) {
                    textFieldPass.error = "Password needs be formed by 6 digits minimum"
                } else {
                    textFieldPass.error = null
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
