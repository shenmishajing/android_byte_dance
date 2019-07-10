package com.zwh.android_byte_dance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, AFragment()).commit()

        color_picker.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
            }
        })
        change_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frame_layout, BFragment())
                    .commit()
            }
        })
    }
}
