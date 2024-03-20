package com.rnscaffold

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.rnscaffold.databinding.ActivityFirstBinding


class FirstActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityFirstBinding;
    lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        val intentFilter = IntentFilter()

        intentFilter.addAction("android.intent.action.TIME_CHECK")

        timeChangeReceiver = TimeChangeReceiver()

        registerReceiver(timeChangeReceiver, intentFilter)

        binding.button1.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.button1 -> {
                Log.d("点击了这里", "急啊急啊急啊就")
                Toast.makeText(this, binding.editText.text.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent("com.rnscaffold.broadcasttest.MyReceiver")
                intent.setPackage(packageName)
                sendBroadcast(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }

    inner class TimeChangeReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(p0, "Time has changed", Toast.LENGTH_SHORT).show()
        }
    }
}