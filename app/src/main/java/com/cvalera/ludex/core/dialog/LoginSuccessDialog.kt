package com.cvalera.ludex.core.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cvalera.ludex.databinding.DialogLoginSuccessBinding
import com.cvalera.ludex.presentation.MainActivity

class LoginSuccessDialog : DialogFragment() {

    companion object {
        fun create(): LoginSuccessDialog = LoginSuccessDialog()
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogLoginSuccessBinding.inflate(requireActivity().layoutInflater)
        binding.btnPositive.setOnClickListener {
            startActivity(MainActivity.create(requireContext()))
            dismiss()
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCancelable(true)
            .create()
    }
}