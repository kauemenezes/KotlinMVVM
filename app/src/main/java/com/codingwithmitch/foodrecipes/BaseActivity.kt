package com.codingwithmitch.foodrecipes

import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar

abstract class BaseActivity: AppCompatActivity() {

    var mProgressBar: ProgressBar? = null


    override fun setContentView(layoutResID: Int) {

        val constraintLayout = layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout =  constraintLayout.findViewById(R.id.activity_content) as FrameLayout
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar)

        layoutInflater.inflate(layoutResID, frameLayout, true)

        super.setContentView(layoutResID)
    }

    fun showProgressBar(visibility: Boolean) {
        mProgressBar?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }
}