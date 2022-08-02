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
import androidx.navigation.fragment.findNavController
import com.campers.R
import jp.wasabeef.richeditor.RichEditor

class CommunityRegistFragment: Fragment() {

    private lateinit var editor: RichEditor

    private val resultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val imageIntent = it.data

            println("이미지 확인 ${imageIntent?.dataString}")
            try{
                editor.insertImage(imageIntent?.dataString, "",300,150)

            }catch (error: Error){
                println("에러")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 키보드 내리기
        val communityRegistLayout = view?.findViewById<ConstraintLayout>(R.id.community_regist_layout)
        var imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        communityRegistLayout?.setOnClickListener {
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        return inflater.inflate(R.layout.fragment_community_regist,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageChange = view.findViewById<ImageButton>(R.id.imageChange)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        editor = view.findViewById(R.id.richEditor)
        editor.setPlaceholder("내용을 입력하세요")

        imageChange.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            resultListener.launch(intent)
        }

        val titleRegist: EditText = view.findViewById(R.id.title_regist)

        saveButton.setOnClickListener {
            println("작성 내용 확인 ${editor.html}")
            findNavController().navigate(
                R.id.communityDetailFragment,
                Bundle().apply {
                    this.putString("title", titleRegist.text.toString())
                    this.putString("content", editor.html)
                }
            )
        }

    }

}