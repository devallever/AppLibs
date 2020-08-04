package org.xm.lib.rate

import android.app.Dialog
import androidx.appcompat.app.AlertDialog

interface RateListener {

    fun onComment(dialog: Dialog?)

    fun onReject(dialog: Dialog?)

    fun onBackPress(dialog: Dialog?)
}