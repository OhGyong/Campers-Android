package com.campers.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.community.CommunityListData
import com.campers.databinding.ActivityCommunityListBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.adapter.CommunityListAdapter
import com.campers.ui.community.viewmodel.CommunityListViewModel

class CommunityListActivity: BaseActivity() {

    private lateinit var mBinding: ActivityCommunityListBinding
    private val mViewModel: CommunityListViewModel by viewModels()
    private lateinit var mCommunityListAdapter: CommunityListAdapter

    private var communityList: ArrayList<CommunityListData> = arrayListOf()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_community_list)

        showCommunityList()
        topActionBarListener()
        observeLiveData()
        setAdapter()
    }

    private fun topActionBarListener() {
        mBinding.topActionBar.ivBack.setOnClickListener { finish() }
    }

    private fun observeLiveData() {
        /**
         * 기본 게시판 게시물 목록
         */
        mViewModel.communityDefaultListData.observe(this, Observer {
            hideLoading()

            if(it.failure != null) {
                println("기본 게시판 게시물 목록 호출 에러")
                return@Observer
            }

            val success = it.success

            if(success?.payload == null) {
                println("기본 게시판 게시물 목록 호출 에러")
                return@Observer
            }

            val communityDefaultListJson = success.payload

            for (i in 0 until communityDefaultListJson.size()) {
                val payloadIndex = communityDefaultListJson.get(i)
                communityList.add(
                    CommunityListData(
                        payloadIndex.asJsonObject.get("id").asInt,
                        payloadIndex.asJsonObject.get("title").toString().trim('"'),
                        payloadIndex.asJsonObject.get("date").toString(),
                        payloadIndex.asJsonObject.get("editDate").toString(),
                        payloadIndex.asJsonObject.get("nickName").toString().trim('"'),
                        payloadIndex.asJsonObject.get("fireCount").asInt,
                        payloadIndex.asJsonObject.get("viewCount").asInt,
                    )
                )
            }
            mCommunityListAdapter.setData(communityList)
        })
    }

    private fun showCommunityList() {
        // TODO : 기본 게시판인지, 사용자 게시판인지 구분 필요
        id = intent.getIntExtra("id", 99999)

        if(id == 99999){
            // TODO : 에러 화면 표시
            return
        }

        showLoading(this)
        mViewModel.getCommunityDefaultList(id)

    }

    private fun setAdapter() {
        // 게시물 리스트
        mCommunityListAdapter = CommunityListAdapter()
        mBinding.communityRecyclerView.adapter = mCommunityListAdapter

    }
}