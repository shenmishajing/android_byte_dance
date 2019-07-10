package com.zwh.android_byte_dance

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_animator.*

class AnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View) {
                Toast.makeText(p0.context, "点击了 button", Toast.LENGTH_SHORT).show()
            }
        })

        button_scale.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var animator = AnimatorInflater.loadAnimator(this@AnimatorActivity, R.animator.scale)
                animator.setTarget(imageView)
                animator.start()
            }
        })

        button_alpha.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var animator = AnimatorInflater.loadAnimator(this@AnimatorActivity, R.animator.alpha)
                animator.setTarget(imageView)
                animator.start()
            }
        })

        button_AnimatorSet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var animator = AnimatorInflater.loadAnimator(this@AnimatorActivity, R.animator.animator_set)
                animator.setTarget(imageView)
                animator.start()
            }
        })
    }
}
