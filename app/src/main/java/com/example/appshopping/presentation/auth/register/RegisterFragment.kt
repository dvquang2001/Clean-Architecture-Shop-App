package com.example.appshopping.presentation.auth.register

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
import com.example.appshopping.databinding.FragmentRegisterBinding
import com.example.appshopping.presentation.main.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register) {

    override fun initView() {
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    binding.inputEmail.etInput.error = uiState.emailError
                    binding.inputPassword.etInput.error = uiState.passwordError
                    binding.inputConfirmedPassword.etInput.error = uiState.confirmedPasswordError
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { uiEffect ->
                    when (uiEffect) {
                        is RegisterViewModel.ViewEffect.RegisterSuccess -> {
                            binding.progressBar.visibility = View.GONE
                            findNavController().navigate(R.id.action_registerFragment_to_initDataFragment)
                        }
                        is RegisterViewModel.ViewEffect.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), uiEffect.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        is RegisterViewModel.ViewEffect.Loading -> {
                        }
                    }
                }
            }
        }
    }

    override fun initViewListener() {
        binding.apply {
            btnRegister.setOnClickListener(this@RegisterFragment)
            binding.ivShowPassword.setOnClickListener(this@RegisterFragment)
            binding.ivShowConfirmedPassword.setOnClickListener(this@RegisterFragment)
            binding.tvNavigateToLoginScreen.setOnClickListener(this@RegisterFragment)
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when (view.id) {
            binding.btnRegister.id -> register()
            binding.ivShowPassword.id -> showHidePassword()
            binding.ivShowConfirmedPassword.id -> showHideConfirmedPassword()
            binding.tvNavigateToLoginScreen.id -> gotoLoginScreen()
        }
    }

    override fun initData() {
        binding.apply {
            inputPassword.etInput.transformationMethod =
                PasswordTransformationMethod.getInstance()
            ivShowPassword.setImageResource(R.drawable.ic_show_password)
        }
        binding.apply {
            inputConfirmedPassword.etInput.transformationMethod =
                PasswordTransformationMethod.getInstance()
            ivShowConfirmedPassword.setImageResource(R.drawable.ic_show_password)
        }
    }

    private fun register() {
        binding.apply {
            val email = inputEmail.etInput.text.toString()
            val password = inputPassword.etInput.text.toString()
            val confirmedPassword = inputConfirmedPassword.etInput.text.toString()
            viewModel.onEvent(
                RegisterViewModel.ViewEvent.RegisterEvent(
                    email,
                    password,
                    confirmedPassword
                )
            )
        }
    }

    private fun gotoLoginScreen() {
       findNavController().navigateUp()
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

    private fun showHideConfirmedPassword() {
        binding.apply {
            if (inputConfirmedPassword.etInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivShowConfirmedPassword.setImageResource(R.drawable.ic_hide_password)
                inputConfirmedPassword.etInput.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                inputConfirmedPassword.etInput.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                ivShowConfirmedPassword.setImageResource(R.drawable.ic_show_password)
            }
        }
    }
}