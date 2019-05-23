package com.sam.sample.shadowlayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sam.lib.shadow.ShadowLayout
import com.sam.lib.shadow.ShadowView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        mShadowView.setOnTouchListener(ShadowView.TouchListener { mShadowView.visibility = View.GONE })

        mShadowLayout.setOnTouchListener(ShadowLayout.TouchListener { mShadowLayout.visibility = View.GONE })


        mShow.setOnClickListener { mShadowLayout.visibility = View.VISIBLE; mShadowView.visibility = View.VISIBLE }

    }
}
