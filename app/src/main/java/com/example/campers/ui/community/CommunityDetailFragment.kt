package com.example.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.campers.R
import com.example.campers.data.community.CommunityDetailData
import com.example.campers.databinding.FragmentCommunityDetailBinding
import com.example.campers.ui.community.viewmodel.CommunityDetailViewModel
import jp.wasabeef.richeditor.RichEditor
import org.json.JSONArray

class CommunityDetailFragment: Fragment() {

    lateinit var binding : FragmentCommunityDetailBinding
    private val viewModel: CommunityDetailViewModel by viewModels()

    private lateinit var communityDetailDataList: CommunityDetailData

    private lateinit var loadEditor: RichEditor



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

        val contentData =  JSONArray(arguments?.getString("content")).getJSONObject(0)
        println(contentData)

        val comment =  JSONArray(arguments?.getString("comment"))

        binding.communityDetailItem=CommunityDetailData(
            contentData["id"] as Int,
            contentData["title"] as String,
            contentData["date"] as String,
            contentData["nickName"] as String,
            contentData["fireCount"] as Int,
            contentData["viewCount"] as Int,
            contentData["hotContents"] as Int,
            contentData["hotDate"] as String,
            contentData["reportCount"] as Int,
            contentData["memberId"] as Int
        )

        loadEditor = view.findViewById(R.id.roadRichEditor)
        loadEditor.html = contentData["contents"].toString()
    }

//    override fun onResume() {
//        super.onResume()
//        println("Fragment 데이터 호출")
//        viewModel.getCommunityDetailData()
//    }


}