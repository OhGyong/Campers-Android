package com.campers.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.community.CommunityCommentData
import com.campers.data.community.CommunityDetailData
import com.campers.databinding.ActivityCommunityDetailBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.adapter.CommunityCommentAdapter
import com.campers.ui.community.viewmodel.CommunityDetailViewModel
import org.json.JSONObject

class CommunityDetailActivity: BaseActivity() {

    private lateinit var mBinding: ActivityCommunityDetailBinding
    private val mViewModel: CommunityDetailViewModel by viewModels()
    private var communityCommentList: ArrayList<CommunityCommentData> = arrayListOf()

    private var type = 0
    private var id = 0
    private var boardId = 0
    private var memberId = 0
    private var boardType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_community_detail)

        topActionBarListener()
        showCommunityDetail()
        observeLiveData()
    }

    private fun topActionBarListener() {
        mBinding.topActionBar.ivBack.setOnClickListener { finish() }
    }

    private fun observeLiveData() {

        mViewModel.hotCommunityDetailData.observe(this, Observer {
            hideLoading()

            if(it.failure != null){
                // TODO : 에러 화면 표시
                return@Observer
            }

            val success = it.success

            if(success == null){
                // TODO : 에러 화면 표시
                return@Observer
            }

            val hotContentData = success.payload.get(0).asJsonArray[0].asJsonObject
            val hotCommentListData = success.payload.get(1).asJsonArray

            mBinding.communityDetailItem = CommunityDetailData(
                hotContentData["id"].asInt,
                hotContentData["title"].asString,
                hotContentData["date"].asString,
                hotContentData["nickName"].asString,
                hotContentData["fireCount"].asInt,
                hotContentData["viewCount"].asInt,
                hotContentData["hotContents"].asInt,
                hotContentData["hotDate"].asString,
                hotContentData["reportCount"].asInt,
                hotContentData["memberId"].asInt
            )
            mBinding.roadRichEditor.html = hotContentData["contents"].asString
            mBinding.roadRichEditor.isFocusable = false // 키보드가 뜨지 않도록 터치 잠금


            /**
             * 댓글이 있는 경우
             * - type == 1, 유저 게시판
             * - type == 2, 기본 게시판
             */
            if(hotCommentListData.size() != 0 && type == 1){
                for (i in 0 until hotCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentData(
                            hotCommentListData[i].asJsonObject["id"].asInt,
                            hotCommentListData[i].asJsonObject["memberBoardContentsId"].asInt,
                            0,
                            hotCommentListData[i].asJsonObject["info"].asString,
                            hotCommentListData[i].asJsonObject["editDate"].asString,
                            hotCommentListData[i].asJsonObject["fireCount"].asInt,
                            hotCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }

                mBinding.communityCommentRecyclerView.adapter = CommunityCommentAdapter(communityCommentList)
            }else if(hotCommentListData.size() != 0 && type == 2){
                for (i in 0 until hotCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentData(
                            hotCommentListData[i].asJsonObject["id"].asInt,
                            0,
                            hotCommentListData[i].asJsonObject["defaultBoardContentsId"].asInt,
                            hotCommentListData[i].asJsonObject["info"].asString,
                            hotCommentListData[i].asJsonObject["editDate"].asString,
                            hotCommentListData[i].asJsonObject["fireCount"].asInt,
                            hotCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }
            }
        })

        mViewModel.communityDefaultDetailData.observe(this, Observer {
            hideLoading()

            if(it.failure != null){
                // TODO : 에러 화면 표시
                return@Observer
            }

            val success = it.success

            if(success == null){
                // TODO : 에러 화면 표시
                return@Observer
            }

            val defaultDetailData = success.payload.get(0).asJsonArray[0].asJsonObject
            val defaultDetailCommentListData = success.payload.get(1).asJsonArray

            mBinding.communityDetailItem = CommunityDetailData(
                defaultDetailData["id"].asInt,
                defaultDetailData["title"].asString,
                defaultDetailData["date"].asString,
                defaultDetailData["nickName"].asString,
                defaultDetailData["fireCount"].asInt,
                defaultDetailData["viewCount"].asInt,
                defaultDetailData["hotContents"].asInt,
                defaultDetailData["hotDate"].toString(),
                0,
                defaultDetailData["memberId"].asInt
            )

            val contents = JSONObject(defaultDetailData["contents"].asJsonPrimitive.asString)["contents"].toString()
            mBinding.roadRichEditor.html = contents
            mBinding.roadRichEditor.isFocusable = false // 키보드가 뜨지 않도록 터치 잠금


            /**
             * 댓글이 있는 경우
             * - type == 1, 유저 게시판
             * - type == 2, 기본 게시판
             */
            if(defaultDetailCommentListData.size() != 0 && type == 1){
                for (i in 0 until defaultDetailCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentData(
                            defaultDetailCommentListData[i].asJsonObject["id"].asInt,
                            defaultDetailCommentListData[i].asJsonObject["memberBoardContentsId"].asInt,
                            0,
                            defaultDetailCommentListData[i].asJsonObject["info"].asString,
                            defaultDetailCommentListData[i].asJsonObject["editDate"].asString,
                            defaultDetailCommentListData[i].asJsonObject["fireCount"].asInt,
                            defaultDetailCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }

                mBinding.communityCommentRecyclerView.adapter = CommunityCommentAdapter(communityCommentList)
            }else if(defaultDetailCommentListData.size() != 0 && type == 2){
                for (i in 0 until defaultDetailCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentData(
                            defaultDetailCommentListData[i].asJsonObject["id"].asInt,
                            0,
                            defaultDetailCommentListData[i].asJsonObject["defaultBoardContentsId"].asInt,
                            defaultDetailCommentListData[i].asJsonObject["info"].asString,
                            defaultDetailCommentListData[i].asJsonObject["editDate"].asString,
                            defaultDetailCommentListData[i].asJsonObject["fireCount"].asInt,
                            defaultDetailCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }
            }
        })

        mViewModel.communityMemberDetailData.observe(this, Observer {
            hideLoading()

            if(it.failure != null){
                // TODO : 에러 화면 표시
                return@Observer
            }

            val success = it.success

            if(success == null){
                // TODO : 에러 화면 표시
                return@Observer
            }

            val memberDetailData = success.payload.get(0).asJsonArray[0].asJsonObject
            val memberDetailCommentListData = success.payload.get(1).asJsonArray

            mBinding.communityDetailItem = CommunityDetailData(
                memberDetailData["id"].asInt,
                memberDetailData["title"].asString,
                memberDetailData["date"].asString,
                memberDetailData["nickName"].asString,
                memberDetailData["fireCount"].asInt,
                memberDetailData["viewCount"].asInt,
                memberDetailData["hotContents"].asInt,
                memberDetailData["hotDate"].toString(),
                0,
                memberDetailData["memberId"].asInt
            )

            val contents = JSONObject(memberDetailData["contents"].asJsonPrimitive.asString)["contents"].toString()
            mBinding.roadRichEditor.html = contents
            mBinding.roadRichEditor.isFocusable = false // 키보드가 뜨지 않도록 터치 잠금


            /**
             * 댓글이 있는 경우
             * - type == 1, 유저 게시판
             * - type == 2, 기본 게시판
             */
            if(memberDetailCommentListData.size() != 0 && type == 1){
                for (i in 0 until memberDetailCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentData(
                            memberDetailCommentListData[i].asJsonObject["id"].asInt,
                            memberDetailCommentListData[i].asJsonObject["memberBoardContentsId"].asInt,
                            0,
                            memberDetailCommentListData[i].asJsonObject["info"].asString,
                            memberDetailCommentListData[i].asJsonObject["editDate"].asString,
                            memberDetailCommentListData[i].asJsonObject["fireCount"].asInt,
                            memberDetailCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }

                mBinding.communityCommentRecyclerView.adapter = CommunityCommentAdapter(communityCommentList)
            }else if(memberDetailCommentListData.size() != 0 && type == 2){
                for (i in 0 until memberDetailCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentData(
                            memberDetailCommentListData[i].asJsonObject["id"].asInt,
                            0,
                            memberDetailCommentListData[i].asJsonObject["defaultBoardContentsId"].asInt,
                            memberDetailCommentListData[i].asJsonObject["info"].asString,
                            memberDetailCommentListData[i].asJsonObject["editDate"].asString,
                            memberDetailCommentListData[i].asJsonObject["fireCount"].asInt,
                            memberDetailCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }
            }
        })
    }


    /**
     * 커뮤니티 상세 화면 표시.
     * - type이나 id을 받을 때 오류가 나면 매우 큰 값으로 설정하여 에러 화면 표시
     * - isHot으로 핫한 상세 게시물인지 체크 (유저 게시판인지 기본 게시판인지 확인)
     */
    private fun showCommunityDetail() {
        showLoading(this)

        type = intent.getIntExtra("type", 99999)
        id = intent.getIntExtra("id", 99999)
        val isHot = intent.getBooleanExtra("isHot", false)

        // 핫 게시물 상세 호출
        if(isHot){
            if(type == 99999 || id == 99999){
                // TODO : 에러 화면 표시
                return
            }
            mViewModel.getHotCommunityDetailData(type, id)
            return
        }

        memberId = intent.getIntExtra("memberId", 99999)
        boardId = intent.getIntExtra("boardId", 99999)
        boardType = intent.getStringExtra("boardType").toString()

        if(memberId == 99999 || boardId == 99999){
            // TODO : 에러 화면 표시
            return
        }

        // 기본 게시판 게시물 상세 호출
        if(boardType == "default") {
            mViewModel.getCommunityDefaultDetailData(boardId, memberId)
        }
        // 사용자 게시판 게심루 상세 호출
        else {
            mViewModel.getCommunityMemberDetailData(boardId, memberId)
        }
    }
}