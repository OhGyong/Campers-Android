package com.example.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.campers.data.community.CommunityCommentList
import com.example.campers.data.community.CommunityDetailData
import com.example.campers.databinding.FragmentCommunityDetailBinding
import com.example.campers.ui.community.adapter.CommunityCommentAdapter
import com.example.campers.ui.community.viewmodel.CommunityDetailViewModel

class CommunityDetailFragment : Fragment() {

    lateinit var binding: FragmentCommunityDetailBinding
    private val viewModel: CommunityDetailViewModel by viewModels()

    private var communityCommentList: ArrayList<CommunityCommentList>? = null

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
            val commentData = it.get(1).asJsonArray

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
            binding.roadRichEditor.isFocusable = false // 키보드가 뜨지 않도록 터치 잠금


            /**
             * 댓글이 있는 경우
             * 1. 첫 if문은 비어있는 array에 add가 불가능하기 때문에 index 0인 첫 데이터만 삽입
             * 2. 두번째 if문은 댓글이 두 개 이상일 떄 삽입.
             */
            if (commentData.size() != 0) {
                communityCommentList = arrayListOf(
                    CommunityCommentList(
                        commentData[0].asJsonObject["id"].asInt,
//                        commentData[0].asJsonObject["defaultBoardContentsId"].asInt,
                        commentData[0].asJsonObject["info"].asString,
                        commentData[0].asJsonObject["editDate"].asString,
                        commentData[0].asJsonObject["fireCount"].asInt,
                        commentData[0].asJsonObject["memberId"].asInt
                    )
                )
            }
            if (commentData.size() >= 1) {
                for (i in 1 until commentData.size()) {
                    communityCommentList!!.add(
                        CommunityCommentList(
                            commentData[i].asJsonObject["id"].asInt,
//                            commentData[i].asJsonObject["defaultBoardContentsId"].asInt,
                            commentData[i].asJsonObject["info"].asString,
                            commentData[i].asJsonObject["editDate"].asString,
                            commentData[i].asJsonObject["fireCount"].asInt,
                            commentData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }
            }
            println(communityCommentList)
            if(commentData.size() != 0){
                binding.communityCommentRecyclerView.adapter = CommunityCommentAdapter(communityCommentList!!)
            }
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