package com.example.campers.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campers.R
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

        saveButton.setOnClickListener {
            println("작성 내용 확인 ${editor.html}")
            findNavController().navigate(
                R.id.communityDetailFragment,
                Bundle().apply {
                    this.putString("content", editor.html)
                }
            )
        }

    }

}