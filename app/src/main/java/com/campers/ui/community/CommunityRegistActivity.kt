package com.campers.ui.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.campers.R
import com.campers.databinding.ActivityCommunityRegistBinding
import com.campers.ui.BaseActivity
import jp.wasabeef.richeditor.RichEditor

class CommunityRegistActivity : BaseActivity() {

    private lateinit var mBinding: ActivityCommunityRegistBinding


    private val resultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val imageIntent = it.data

            println("이미지 확인 ${imageIntent?.dataString}")
            try {
                mBinding.richEditor.insertImage(imageIntent?.dataString, "", 300, 150)

            } catch (error: Error) {
                println("에러")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommunityRegistBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // reachEditor 설정
        mBinding.richEditor.setPlaceholder("내용을 입력하세요")

        // 키보드 내리기
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mBinding.communityRegistLayout.setOnClickListener {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        // 이미지 추가
        mBinding.ibImgAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            resultListener.launch(intent)
        }

        //        saveButton.setOnClickListener {
//            println("작성 내용 확인 ${editor.html}")
//            findNavController().navigate(
//                R.id.communityDetailFragment,
//                Bundle().apply {
//                    this.putString("title", titleRegist.text.toString())
//                    this.putString("content", editor.html)
//                }
//            )
//        }
    }
}