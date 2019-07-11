package com.zwh.android_byte_dance

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_clock.*

class ClockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        var isShowAnalog = getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean("isShowAnalog", true)

        clock.isShowAnalog = isShowAnalog

        clock.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                clock.isShowAnalog = !clock.isShowAnalog
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean("isShowAnalog", clock.isShowAnalog).apply()
    }
}
