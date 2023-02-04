package com.example.appshopping.presentation.auth.reset_password

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding, ResetPasswordViewModel>(R.layout.fragment_forgot_password) {

    private fun gotoCheckEmailScreen() {
        binding.apply {
            viewModel.onEvent(ResetPasswordViewModel.ViewEvent.SendEvent(inputEmail.etInput.text.toString()))
        }
    }

    private fun backToLoginScreen() {
        val action = ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when (view.id) {
            binding.btnSend.id -> gotoCheckEmailScreen()
            binding.tvNavigateToLogin.id -> backToLoginScreen()
        }
    }

    override fun initView() {

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    binding.inputEmail.etInput.error = uiState.emailError
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { uiEffect ->
                    when(uiEffect) {
                        is ResetPasswordViewModel.ViewEffect.SendComplete -> {
                            val action = ResetPasswordFragmentDirections.actionResetPasswordFragmentToCheckEmailFragment()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

    override fun initViewListener() {
        binding.apply {
            btnSend.setOnClickListener(this@ResetPasswordFragment)
            tvNavigateToLogin.setOnClickListener(this@ResetPasswordFragment)
        }
    }

    override fun initData() {
    }
}