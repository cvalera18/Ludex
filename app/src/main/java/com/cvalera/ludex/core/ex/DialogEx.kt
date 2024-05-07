package com.cvalera.ludex.core.ex

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cvalera.ludex.core.dialog.DialogFragmentLauncher

fun DialogFragment.show(launcher: DialogFragmentLauncher, activity: FragmentActivity) {
    launcher.show(this, activity)
}