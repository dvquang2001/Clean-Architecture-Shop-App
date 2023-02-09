package com.example.appshopping.presentation.main.home.user_screen

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.*
import com.example.appshopping.domain.usecase.auth.login.LoginParam
import com.example.appshopping.domain.usecase.main.get_user.UserParam
import com.example.appshopping.presentation.auth.LoginActivity
import com.example.appshopping.presentation.main.product.ProductActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment :
    BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>(R.layout.fragment_user_info) {

    private lateinit var dialogNameBinding: LayoutDialogNameBinding
    private lateinit var dialogGenderBinding: LayoutDialogGenderBinding
    private lateinit var dialogLogoutBinding: LayoutDialogLogoutBinding
    private lateinit var dialogChangePasswordBinding: LayoutDialogChangePasswordBinding
    private var id: String? = null

    override fun initView() {
    }

    private fun handleLogout(loggedIn: Boolean) {
        if(!loggedIn) {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    id = it.id
                    binding.tvGreeting.text = it.name
                    binding.tvEmail.text = it.email
                    binding.tvGender.text = it.gender
                    handleLogout(it.loggedIn)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when(effect) {
                        is UserInfoViewModel.ViewEffect.ChangeUserInfoSuccess -> {
                            showSnackBar(getString(R.string.change_success))
                        }
                        is UserInfoViewModel.ViewEffect.ChangeUserInfoFail -> {
                            showSnackBar(effect.error)
                        }
                        is UserInfoViewModel.ViewEffect.ChangePasswordSuccess -> {
                            showSnackBar(getString(R.string.change_success))
                        }
                        is UserInfoViewModel.ViewEffect.ChangePasswordFail -> {
                            showSnackBar(effect.error)
                        }
                    }
                }
            }
        }
    }

    override fun initViewListener() {
        binding.apply {
            featureName.itemView.setOnClickListener(this@UserInfoFragment)
            featureGender.itemView.setOnClickListener(this@UserInfoFragment)
            featureChangePassword.itemView.setOnClickListener(this@UserInfoFragment)
            btnLogout.setOnClickListener(this@UserInfoFragment)
            layoutHeader.iconEnd.setOnClickListener(this@UserInfoFragment)
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when (view.id) {
            binding.featureName.itemView.id -> showNameDialog()
            binding.featureGender.itemView.id -> showGenderDialog()
            binding.featureChangePassword.itemView.id -> showChangePasswordScreen()
            binding.btnLogout.id -> logoutToLoginScreen()
            binding.layoutHeader.iconEnd.id -> navigateToProductActivity()
        }
    }

    private fun navigateToProductActivity() {
        startActivity(Intent(requireActivity(), ProductActivity::class.java))
    }

    private fun showSnackBar(text: String) {
        val snackBar = Snackbar.make(this@UserInfoFragment.requireView(),text,Snackbar.LENGTH_LONG).setAction("Action",null)
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.view.setBackgroundColor(Color.BLACK)
        snackBar.show()
    }

    private fun showNameDialog() {
        val dialog = Dialog(requireContext())
        dialogNameBinding = LayoutDialogNameBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogNameBinding.root)
        }

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER
        window?.attributes = windowAttributes

        dialogNameBinding.inputName.etInput.setText(
            binding.tvGreeting.text.toString(),
            TextView.BufferType.EDITABLE
        )
        dialogNameBinding.btnSave.setOnClickListener {
            viewModel.onEvent(
                UserInfoViewModel.ViewEvent.ChangeUserInfo(
                    UserParam(
                        id = id ?: "",
                        name = dialogNameBinding.inputName.etInput.text.toString(),
                        email = "",
                        gender = "",
                        accountBalance = ""
                    )
                )
            )
           dialog.dismiss()
        }
        dialogNameBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showGenderDialog() {
        val dialog = Dialog(requireContext())
        dialogGenderBinding =
            LayoutDialogGenderBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogGenderBinding.root)
        }

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER
        window?.attributes = windowAttributes

        dialogGenderBinding.tvMale.setOnClickListener {
            changeUserGender(dialogGenderBinding.tvMale.text.toString())
            dialog.dismiss()
        }
        dialogGenderBinding.tvFeMale.setOnClickListener {
            changeUserGender(dialogGenderBinding.tvFeMale.text.toString())
            dialog.dismiss()
        }
        dialogGenderBinding.tvOther.setOnClickListener {
            changeUserGender(dialogGenderBinding.tvOther.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun changeUserGender(gender: String) {
        viewModel.onEvent(
            UserInfoViewModel.ViewEvent.ChangeUserInfo(
                UserParam(
                    id = id ?: "",
                    name = "",
                    email = "",
                    gender = gender,
                    accountBalance = ""
                )
            )
        )
    }
    private fun showChangePasswordScreen() {
        val dialog = Dialog(requireContext())
        dialogChangePasswordBinding =
            LayoutDialogChangePasswordBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogChangePasswordBinding.root)
        }

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER
        window?.attributes = windowAttributes

        dialogChangePasswordBinding.btnChange.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val password = dialogChangePasswordBinding.inputPassword.etInput.text.toString()
            val newPassword = dialogChangePasswordBinding.inputNewPassword.etInput.text.toString()
            val confirmedPassword = dialogChangePasswordBinding.inputConfirmedPassword.etInput.text.toString()
            viewModel.onEvent(
                UserInfoViewModel.ViewEvent.ChangePassword(
                    LoginParam(email, password),
                    newPassword,
                    confirmedPassword
                )
            )
        }
        dialogChangePasswordBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun logoutToLoginScreen() {
        val dialog = Dialog(requireContext())
        dialogLogoutBinding =
            LayoutDialogLogoutBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogLogoutBinding.root)
        }

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER
        window?.attributes = windowAttributes

        dialogLogoutBinding.btnConfirmLogout.setOnClickListener {
            viewModel.onEvent(UserInfoViewModel.ViewEvent.Logout)

        }
        dialogLogoutBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun initData() {
        viewModel.onEvent(UserInfoViewModel.ViewEvent.CheckUserData)
    }
}