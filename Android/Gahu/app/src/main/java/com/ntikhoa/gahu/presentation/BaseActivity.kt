package com.ntikhoa.gahu.presentation

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun displayProgressBar(isVisible: Boolean)
}