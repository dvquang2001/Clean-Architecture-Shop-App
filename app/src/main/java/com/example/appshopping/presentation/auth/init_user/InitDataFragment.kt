package com.example.appshopping.presentation.auth.init_user

import android.content.Intent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentInitUserBinding
import com.example.appshopping.presentation.main.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitDataFragment :
    BaseFragment<FragmentInitUserBinding, InitDataViewModel>(R.layout.fragment_init_user) {

    override fun initView() {
        viewModel.onEvent(InitDataViewModel.ViewEvent.InitUserData)
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is InitDataViewModel.ViewEffect.GetInitUserSuccess -> {
                            binding.progressBarLoading.visibility = View.GONE
                            val intent = Intent(
                                requireActivity(), MainActivity::class.java
                            )
                            startActivity(intent)
                            activity?.finish()
                        }
                        is InitDataViewModel.ViewEffect.GetInitUserFail -> {
                            binding.progressBarLoading.visibility = View.GONE
                            binding.tvError.text = effect.error
                        }
                        is InitDataViewModel.ViewEffect.Loading -> {
                            binding.progressBarLoading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun initViewListener() {
    }

    override fun initData() {
    }
}