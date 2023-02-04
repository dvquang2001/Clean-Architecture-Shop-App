package com.example.appshopping.presentation.auth.reset_password

import androidx.navigation.fragment.findNavController
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentCheckMailBinding

class CheckEmailFragment: BaseFragment<FragmentCheckMailBinding,CheckEmailViewModel>(R.layout.fragment_check_mail) {

    override fun initView() {

    }

    override fun initObserver() {
    }

    override fun initViewListener() {
        binding.btnSend.setOnClickListener {
            val action = CheckEmailFragmentDirections.actionCheckEmailFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    override fun initData() {
    }
}