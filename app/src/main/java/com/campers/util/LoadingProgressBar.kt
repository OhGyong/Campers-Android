package com.campers.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.campers.R

class LoadingProgressBar constructor(context: Context) : Dialog(context) {
    init {
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // dialog의 dim 처리 배경 제거

        setContentView(R.layout.loading_progress_bar)
    }
}