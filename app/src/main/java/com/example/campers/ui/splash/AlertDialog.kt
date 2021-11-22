package com.example.campers.ui.splash

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * MaterialAlertDialogBuilder에 EditText를 사용하기 위한 클래스
 */
class AlertDialog {
    fun materialAlertDialogBuilder(context: Context){
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle("닉네임을 입력해주세요.")

        val constraintLayout = getEditTextLayout(context)
        builder.setView(constraintLayout)

        val textInputLayout = constraintLayout.findViewWithTag<TextInputLayout>("textInputLayoutTag")
        val textInputEditText = constraintLayout.findViewWithTag<TextInputEditText>("textInputEditTextTag")

        builder.setPositiveButton("Submit"){ _, _ ->
        }

        // alert dialog other buttons
        builder.setNeutralButton("Cancel",null)

        // set dialog non cancelable
        builder.setCancelable(false)

        // finally, create the alert dialog and show it
        val dialog = builder.create()

        dialog.show()

        // initially disable the positive button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

        // edit text text change listener
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int,
                                           p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int,
                                       p2: Int, p3: Int) {
                if (p0.isNullOrBlank()){
                    textInputLayout.error = "Name is required."
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .isEnabled = false
                }else{
                    textInputLayout.error = ""
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .isEnabled = true
                }
            }
        })
    }

    private fun getEditTextLayout(context: Context): ConstraintLayout {
        val constraintLayout = ConstraintLayout(context)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        constraintLayout.layoutParams = layoutParams
        constraintLayout.id = View.generateViewId()

        val textInputLayout = TextInputLayout(context)
        textInputLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        layoutParams.setMargins(
            32.toDp(context),
            8.toDp(context),
            32.toDp(context),
            8.toDp(context)
        )

        textInputLayout.layoutParams = layoutParams
        textInputLayout.hint = "홍길동"
        textInputLayout.id = View.generateViewId()
        textInputLayout.tag = "textInputLayoutTag"


        val textInputEditText = TextInputEditText(context)
        textInputEditText.id = View.generateViewId()
        textInputEditText.tag = "textInputEditTextTag"

        textInputLayout.addView(textInputEditText)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintLayout.addView(textInputLayout)
        return constraintLayout
    }

    private fun Int.toDp(context: Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    ).toInt()
}