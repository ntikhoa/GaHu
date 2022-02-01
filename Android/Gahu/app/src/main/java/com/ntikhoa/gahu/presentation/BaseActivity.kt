package com.ntikhoa.gahu.presentation

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.NETWORK_ERROR_MSG
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.TIMEOUT_ERROR_MSG
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.UNKNOWN_ERROR

abstract class BaseActivity : AppCompatActivity() {

    abstract fun displayProgressBar(isVisible: Boolean)

    fun networkError() {
        Toast.makeText(this, NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show()
    }

    fun unknownError() {
        Toast.makeText(this, UNKNOWN_ERROR, Toast.LENGTH_SHORT).show()
    }

    fun timeoutError() {
        Toast.makeText(this, TIMEOUT_ERROR_MSG, Toast.LENGTH_SHORT).show()
    }
}