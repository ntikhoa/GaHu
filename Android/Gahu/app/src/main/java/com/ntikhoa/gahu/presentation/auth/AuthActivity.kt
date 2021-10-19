package com.ntikhoa.gahu.presentation.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.NETWORK_ERROR_MSG
import com.ntikhoa.gahu.databinding.ActivityAuthBinding
import com.ntikhoa.gahu.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        val isLogin = intent.getBooleanExtra(Constants.EXTRA_LOGIN, false)
        if (isLogin) {
            findNavController(R.id.auth_fragments_container)
                .navigate(R.id.action_launcherFragment_to_loginFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun displayProgressBar(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}