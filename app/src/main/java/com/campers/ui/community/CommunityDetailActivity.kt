package com.campers.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.community.CommunityCommentList
import com.campers.data.community.CommunityDetailData
import com.campers.databinding.ActivityCommunityDetailBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.adapter.CommunityCommentAdapter
import com.campers.ui.community.viewmodel.CommunityDetailViewModel

class CommunityDetailActivity: BaseActivity() {

    private lateinit var mBinding: ActivityCommunityDetailBinding
    private val mViewModel: CommunityDetailViewModel by viewModels()

    private var communityCommentList: ArrayList<CommunityCommentList> = arrayListOf()

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
             */
            if(hotCommentListData.size() != 0 ){
                for (i in 0 until hotCommentListData.size()) {
                    communityCommentList.add(
                        CommunityCommentList(
                            hotCommentListData[i].asJsonObject["id"].asInt,
//                            hotCommentListData[i].asJsonObject["defaultBoardContentsId"].asInt,
                            hotCommentListData[i].asJsonObject["info"].asString,
                            hotCommentListData[i].asJsonObject["editDate"].asString,
                            hotCommentListData[i].asJsonObject["fireCount"].asInt,
                            hotCommentListData[i].asJsonObject["memberId"].asInt
                        )
                    )
                }

                mBinding.communityCommentRecyclerView.adapter = CommunityCommentAdapter(communityCommentList)
            }
        })
    }


    /**
     * 커뮤니티 상세 화면 표시.
     * - type이나 id을 받을 때 오류가 나면 매우 큰 값으로 설정하여 에러 화면 표시
     * - isHot으로 핫한 상세 게시물인지 체크 (유저 게시판인지 기본 게시판인지 확인)
     */
    private fun showCommunityDetail() {
        val type = intent.getIntExtra("type", 99999)
        val id = intent.getIntExtra("id", 99999)
        val isHot = intent.getBooleanExtra("isHot", false)

        if(type == 99999 || id == 99999){
            // TODO : 에러 화면 표시
            return
        }

        if(isHot){
            showLoading(this)
            mViewModel.getHotCommunityDetailData(type, id)
        }
    }
}