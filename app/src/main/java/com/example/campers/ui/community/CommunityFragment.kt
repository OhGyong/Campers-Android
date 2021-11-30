package com.example.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.R
import com.example.campers.data.community.CommunityData
import com.example.campers.ui.community.adapter.CommunityAdapter

class CommunityFragment: Fragment() {
//    private val viewModel: CommunityViewModel by viewModels()

    var boardList: ArrayList<CommunityData> = arrayListOf(
        CommunityData("홍길동", "게시글 9번입니다.", 10, 1, 15),
        CommunityData("홍길동", "게시글 8번입니다.",1,1,1),
        CommunityData("홍길동", "게시글 9번입니다.", 10, 1, 15),
        CommunityData("홍길동", "게시글 8번입니다.",1,1,1),
        CommunityData("홍길동", "게시글 9번입니다.", 10, 1, 15),
        CommunityData("홍길동", "게시글 8번입니다.",1,1,1),
        CommunityData("철수", "게시글 7번입니다.",1,1,1),
        CommunityData("철수", "게시글 6번입니다.",1,1,1),
        CommunityData("영희", "게시글 5번입니다.",1,1,1),
        CommunityData("철수", "게시글 4번입니다.",1,1,1),
        CommunityData("hallym", "게시글 3번입니다.",1,1,1),
        CommunityData("철수", "게시글 2번입니다.",1,1,1),
        CommunityData("맹구", "게시글 1번입니다.",1,1,1)
    )

    private lateinit var communityRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("sadfdsafsdaf")
        communityRecyclerView = view.findViewById(R.id.community_recyclerView)
        communityRecyclerView.adapter = CommunityAdapter(boardList)

        val communityBtn = view.findViewById<Button>(R.id.community_btn)
        communityBtn.setOnClickListener {
            findNavController().navigate(R.id.communityRegistFragment)
        }


    }
}