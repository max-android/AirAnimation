package com.my_project.airanimation.utilities

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.my_project.airanimation.R

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class DialogUtils (private val context: Context) {

    fun showNoConnect() {
        MaterialDialog(context).show {
            title(R.string.no_connect)
            message(R.string.no_connect_message)
            cornerRadius(16f)
            icon(R.drawable.ic_signal_wifi_off)
            positiveButton(R.string.ok_value) { dialog ->
                dialog.dismiss()
            }
        }
    }
}