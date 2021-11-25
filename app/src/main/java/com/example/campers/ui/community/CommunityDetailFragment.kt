package com.example.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        loadEditor = view.findViewById(R.id.roadRichEditor)
        loadEditor.html = arguments?.getString("content")

    }
}