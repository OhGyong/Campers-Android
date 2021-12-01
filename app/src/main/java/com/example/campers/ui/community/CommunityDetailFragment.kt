package com.example.campers.ui.community

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.campers.R
import jp.wasabeef.richeditor.RichEditor

class CommunityDetailFragment: Fragment() {

    private lateinit var loadEditor: RichEditor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community_detail, container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleDetail: TextView = view.findViewById(R.id.title_detail)
        titleDetail.paintFlags = Paint.FAKE_BOLD_TEXT_FLAG
        titleDetail.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F)
        titleDetail.setTextColor(Color.BLACK)
        titleDetail.text = arguments?.getString("title")

        loadEditor = view.findViewById(R.id.roadRichEditor)
        loadEditor.html = arguments?.getString("content")

    }
}