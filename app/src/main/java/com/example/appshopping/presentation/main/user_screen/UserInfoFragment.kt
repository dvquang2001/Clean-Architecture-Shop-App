package com.example.appshopping.presentation.main.user_screen

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment: BaseFragment<FragmentUserInfoBinding,UserInfoViewModel>(R.layout.fragment_user_info) {

    override fun initView() {
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.tvProfile.text = it.currentUser?.email
                }
            }
        }
    }

    override fun initViewListener() {
    }

    override fun initData() {
        viewModel.onEvent(UserInfoViewModel.ViewEvent.CheckUserData)
    }
}