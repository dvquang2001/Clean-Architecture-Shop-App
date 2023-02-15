package com.example.appshopping.presentation.main.home.setting_screen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import com.example.appshopping.R
import com.example.appshopping.base.BaseFragment
import com.example.appshopping.databinding.FragmentSettingBinding
import com.example.appshopping.databinding.LayoutDialogSelectLanguageBinding

class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    private lateinit var dialogLanguageBinding: LayoutDialogSelectLanguageBinding

    override fun initView() {
    }

    override fun initObserver() {
    }

    override fun initViewListener() {
        binding.apply {
            featureLanguage.itemView.setOnClickListener(this@SettingFragment)
            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    override fun onViewClicked(view: View) {
        super.onViewClicked(view)
        when(view.id) {
            binding.featureLanguage.itemView.id -> openLanguageDialog()
        }
    }

    private fun openLanguageDialog() {
        val dialog = Dialog(requireContext())
        dialogLanguageBinding = LayoutDialogSelectLanguageBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogLanguageBinding.root)
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
        dialog.show()
    }


    override fun initData() {
    }
}