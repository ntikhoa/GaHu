package com.ntikhoa.gahu.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.databinding.FragmentLoginBinding
import java.lang.Exception


class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        setBtnOnClickListener()
    }

    private fun setBtnOnClickListener() {
        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            btnRegister.setOnClickListener(this@LoginFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                println("SIGN IN CLICKED")
            }
            R.id.btn_register -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            else -> {
                throw Exception("Button id is not handle")
            }
        }
    }
}