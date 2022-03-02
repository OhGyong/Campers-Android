package com.example.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.campers.data.community.CommunityDetailData
import com.example.campers.databinding.FragmentCommunityDetailBinding
import com.example.campers.ui.community.viewmodel.CommunityDetailViewModel

class CommunityDetailFragment : Fragment() {

    lateinit var binding: FragmentCommunityDetailBinding
    private val viewModel: CommunityDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.communityDetailData.observe(viewLifecycleOwner, {
            val contentData = it.get(0).asJsonArray[0].asJsonObject
            val commentData = it.get(1)

            binding.communityDetailItem = CommunityDetailData(
                contentData["id"].asInt,
                contentData["title"].asString,
                contentData["date"].asString,
                contentData["nickName"].asString,
                contentData["fireCount"].asInt,
                contentData["viewCount"].asInt,
                contentData["hotContents"].asInt,
                contentData["hotDate"].asString,
                contentData["reportCount"].asInt,
                contentData["memberId"].asInt
            )
            binding.roadRichEditor.html = contentData["contents"].asString
        })
    }

    override fun onResume() {
        super.onResume()
        println("Fragment 데이터 호출")
        viewModel.getCommunityDetailData(
            requireArguments().getInt("type"),
            requireArguments().getInt("id")
        )
    }


}