@file:Suppress("DEPRECATION")

package com.example.merchantapp.classes

import android.app.Fragment
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.replaceFragmenty(fragment: Fragment,
                                       allowStateLoss: Boolean = false,
                                       @IdRes containerViewId: Int) {
    val ft = fragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment)
    if (!supportFragmentManager.isStateSaved) {
        ft.commit()
    } else if (allowStateLoss) {
        ft.commitAllowingStateLoss()
    }
}