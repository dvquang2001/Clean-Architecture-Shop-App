package com.example.appshopping.presentation.auth.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentLoginBinding
import com.example.appshopping.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    override fun initView() {
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    handleLogin(uiState.loggedIn)
                    binding.inputEmail.etInput.error = uiState.emailError
                    binding.inputPassword.etInput.error = uiState.passwordError
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { uiEffect ->
                    when (uiEffect) {
                        is LoginViewModel.ViewEffect.LoginSuccess -> {
                            binding.progressBar.visibility = View.GONE
                            val intent = Intent(
                                requireActivity(), MainActivity::class.java
                            )
                            startActivity(intent)
                            activity?.finish()
                        }
                        is LoginViewModel.ViewEffect.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), uiEffect.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        is LoginViewModel.ViewEffect.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun initViewListener() {
        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            tvForgotPassword.setOnClickListener(this@LoginFragment)
            tvNavigateToRegister.setOnClickListener(this@LoginFragment)
            ivShowPassword.setOnClickListener(this@LoginFragment)
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when (view.id) {
            binding.btnLogin.id -> login()
            binding.ivShowPassword.id -> showHidePassword()
            binding.tvNavigateToRegister.id -> gotoRegisterScreen()
            binding.tvForgotPassword.id -> gotoResetEmailScreen()
        }
    }

    override fun initData() {
        binding.apply {
            inputPassword.etInput.transformationMethod =
                PasswordTransformationMethod.getInstance()
            ivShowPassword.setImageResource(R.drawable.ic_show_password)
        }
        checkLogin()
    }

    private fun checkLogin() {
        viewModel.onEvent(LoginViewModel.ViewEvent.CheckLogin)
    }

    private fun login() {
        binding.apply {
            val email = inputEmail.etInput.text.toString()
            val password = inputPassword.etInput.text.toString()
            viewModel.onEvent(LoginViewModel.ViewEvent.LoginEvent(email, password))
        }
    }

    private fun gotoRegisterScreen() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun gotoResetEmailScreen() {
        val action = LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment()
        findNavController().navigate(action)
    }

    private fun showHidePassword() {
        binding.apply {
            if (inputPassword.etInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivShowPassword.setImageResource(R.drawable.ic_hide_password)
                inputPassword.etInput.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                inputPassword.etInput.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                ivShowPassword.setImageResource(R.drawable.ic_show_password)
            }
        }
    }

    private fun handleLogin(loggedIn: Boolean) {
        if (loggedIn) {
//            val intent = Intent(
//                requireActivity(), MainActivity::class.java
//            )
//            startActivity(intent)
//            activity?.finish()
        }
    }


}