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

class CommunityListFragment: Fragment() {

    var boardList: ArrayList<CommunityData> = arrayListOf(
        CommunityData("user2", "게시글 13번입니다.", 4, 2, 23),
        CommunityData("user2", "게시글 12번입니다.",3,3,21),
        CommunityData("user1", "게시글 11번입니다.", 2, 0, 3),
        CommunityData("user4", "게시글 10번입니다.",4,4,12),
        CommunityData("user5", "게시글 9번입니다.", 5, 2, 16),
        CommunityData("user3", "게시글 8번입니다.",1,3,15),
        CommunityData("user1", "게시글 7번입니다.",2,1,18),
        CommunityData("user1", "게시글 6번입니다.",5,2,15),
        CommunityData("user5", "게시글 5번입니다.",4,5,13),
        CommunityData("user4", "게시글 4번입니다.",2,3,12),
        CommunityData("user3", "게시글 3번입니다.",3,4,7),
        CommunityData("user2", "게시글 2번입니다.",4,5,4),
        CommunityData("user1", "게시글 1번입니다.",1,2,27)
    )

    private lateinit var communityRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communityRecyclerView = view.findViewById(R.id.community_recyclerView)
        communityRecyclerView.adapter = CommunityAdapter(boardList)

        val communityBtn = view.findViewById<Button>(R.id.community_btn)
        communityBtn.setOnClickListener {
            findNavController().navigate(R.id.communityRegistFragment)
        }


    }
}