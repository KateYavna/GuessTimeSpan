package com.example.guesstimespan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    btStart.setOnClickListener {
        var random: Int = (5..20).random()
        progressBar.visibility = View.VISIBLE
        async {
            delay(random.toLong()*1000)
            progressBar.visibility = View.INVISIBLE
            etGuessTime.visibility = View.VISIBLE
            etGuessTime.requestFocus()
            btSure.visibility = View.VISIBLE
            etGuessTime.visibility = View.VISIBLE

            etGuessTime.afterTextChanged { btSure.setOnClickListener {
                Log.d("MyLog", " your time is "+ etGuessTime.text.toString())
                Log.d("MyLog", " real time is "+ random.toString())
                if (etGuessTime.text.toString().equals(random.toString())) tvFinish.text = "You are win"
                else tvFinish.text = "You lose, it's been ${random} seconds"
            } }}
    }

    }
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}