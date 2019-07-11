package com.zwh.android_byte_dance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Switch
import androidx.core.graphics.alpha
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var counter = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, ImageActivity::class.java))
            }
        })

        recycle_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, RecycleActivity::class.java))
            }
        })

        animation_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, AnimatorActivity::class.java))
            }
        })

        fragment_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, FragmentActivity::class.java))
            }
        })

        clock_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, ClockActivity::class.java))
            }
        })

        lottie_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, LottieActivity::class.java))
            }
        })
    }
}
