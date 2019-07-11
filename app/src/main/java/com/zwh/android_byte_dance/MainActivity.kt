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

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, p1: Int, p2: Boolean) {
                imageView1.alpha = seekBar.progress.toFloat() / 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        switch1.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton, isChecked: Boolean) {
                if (!isChecked) {
                    switch.text = "picture1"
                    imageView1.setImageResource(R.drawable.download)
                } else {
                    switch.text = "picture2"
                    imageView1.setImageResource(R.drawable.download_1)
                }
            }
        })

        button2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                print("clicked")
                startActivity(Intent(this@MainActivity, RecycleActivity::class.java))
            }
        })

        button3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, AnimatorActivity::class.java))
            }
        })

        button4.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, FragmentActivity::class.java))
            }
        })

        button5.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, ClockActivity::class.java))
            }
        })

        chapter_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, LottieActivity::class.java))
            }
        })
    }

    fun ChangeButtononClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                if (counter == 0) {
                    text.setText("Hello World!")
                    counter = 1
                } else {
                    text.setText("Hello!")
                    counter = 0
                }
            }
        }
    }
}
