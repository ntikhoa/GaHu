package com.ntikhoa.gahu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ntikhoa.gahu.presentation.BaseActivity
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(sessionManager.token)
    }

    override fun displayProgressBar(isVisible: Boolean) {
        TODO("Not yet implemented")
    }
}