package com.campers.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.campers.R
import com.campers.databinding.CommonDialogInputBinding

class CommonInputDialog(context: Context): Dialog(context, R.style.CommonInputDialog) {

    interface BtnClickListener {
        fun onBtnClick()
    }

    private var mBinding = CommonDialogInputBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(mBinding.root)
    }

    class Builder(context: Context) {
        private val mDialog = CommonInputDialog(context)

        fun setTitle(text: String): Builder {
            mDialog.mBinding.tvTitle.text = text
            return this
        }

        fun setEditText(hint: String): Builder {
            mDialog.mBinding.etContent.hint = hint
            return this
        }

        fun setCancelBtn(): Builder {
            mDialog.mBinding.ctCancelWithCheck.visibility = View.VISIBLE
            mDialog.mBinding.btnOnlyCheck.visibility = View.GONE
            mDialog.mBinding.btnCancel.setOnClickListener {
                mDialog.dismiss()
            }
            return this
        }

        // 클릭 리스너 이벤트를 사용할 때 호출
        fun setCheckBtn(rightBtnClickListener: BtnClickListener? = null): Builder {
            mDialog.mBinding.btnCheck.setOnClickListener {
                rightBtnClickListener?.onBtnClick()
            }
            return this
        }

        // 확인과 함께 다이얼로그 닫을 때 호출
        fun setCheckBtn(): Builder {
            mDialog.mBinding.ctCancelWithCheck.visibility = View.GONE
            mDialog.mBinding.btnOnlyCheck.visibility = View.VISIBLE
            mDialog.mBinding.btnOnlyCheck.setOnClickListener {
                mDialog.dismiss()
            }
            return this
        }

        fun show(): CommonInputDialog {
            mDialog.show()
            return mDialog
        }
    }
}