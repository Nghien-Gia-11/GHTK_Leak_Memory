package com.example.ghtk_leak_memory

import android.content.BroadcastReceiver
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class LeakActivity : AppCompatActivity() {


    private val handler = Handler(Looper.getMainLooper())
    private var cnt = 0
    private lateinit var myReceiver: BroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)


        /*memory leak bằng cách khởi tạo context thừa thãi*/
        MainActivity.context = this
        finish()

        /*        myReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        handler.postDelayed({
                            cnt++
                        }, 1000)
                    }
                }
                // cpu leak bằng cách tạo broadcast và vòng lặp nhưng không hủy
                registerReceiver(myReceiver, IntentFilter("SOME_ACTION"), RECEIVER_NOT_EXPORTED)
                finish()*/
    }


}