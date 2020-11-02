package com.sumcorp.bharatagri.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sumcorp.bharatagri.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)

    }
}