package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle.Event.downTo
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val timerHandler = Handler(Looper.getMainLooper()) {
        textView.text = it.what.toString()
        true //return true
    }
    val textView: TextView by lazy {
        findViewById(R.id.textView)
    }
    val button: Button by lazy {
        findViewById(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scope = CoroutineScope(Job() + Dispatchers.Default)
        button.setOnClickListener() {
            scope.launch {
                repeat(100) {
                    (100-it).toString().run {
                        Log.d("Countdown", this)
                        withContext(Dispatchers.Main) {textView.text = this@run}
                    }
                    delay(1000)
                }
            }
        }

        /*Thread {
            repeat(100) {//for(i in 100 >= downTo() >= (1)) {
                //Log.d("Countdown", (100 - it).toString())
                val msg = Message.obtain()
                //try, catch
                Thread.sleep(1000)

                timerHandler.sendEmptyMessage(100 - it)
            }
        }.start()*/
    }
}