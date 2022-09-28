package com.campers.ui.community

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.community.CommunityListData
import com.campers.databinding.ActivityCommunityListBinding
import com.campers.databinding.ItemListCommunityBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.adapter.CommunityListAdapter
import com.campers.ui.community.viewmodel.CommunityListViewModel
import com.google.gson.Gson

class CommunityListActivity: BaseActivity() {

    private lateinit var mBinding: ActivityCommunityListBinding
    private val mViewModel: CommunityListViewModel by viewModels()
    private lateinit var mCommunityListAdapter: CommunityListAdapter

    private var communityList: ArrayList<CommunityListData> = arrayListOf()

    private var id = 0
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_community_list)

        showCommunityList()
        topActionBarListener()
        observeLiveData()
        setAdapter()
        clickListener()
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
            communityList = Gson()
                .fromJson(communityDefaultListJson, Array<CommunityListData>::class.java)
                .toCollection(ArrayList())

            mCommunityListAdapter.setData(communityList)
        })
    }

    private fun showCommunityList() {
        // TODO : 기본 게시판인지, 사용자 게시판인지 구분 필요
        id = intent.getIntExtra("id", 99999)
        type = intent.getStringExtra("type").toString()

        if(id == 99999){
            // TODO : 에러 화면 표시
            return
        }

        showLoading(this)
        if(type == "default") {
            mViewModel.getCommunityDefaultList(id)
        }else {
            // todo : 사용자 게시판 호출
        }



    }

    private fun setAdapter() {
        // 게시물 리스트
        mCommunityListAdapter = CommunityListAdapter()
        mBinding.communityRecyclerView.adapter = mCommunityListAdapter

    }

    private fun clickListener() {
        mCommunityListAdapter.setOnItemClickListener(object : CommunityListAdapter.OnItemClickListener {
            override fun setOnItemClick(
                binding: ItemListCommunityBinding,
                data: CommunityListData
            ) {
                val intent = Intent(this@CommunityListActivity, CommunityDetailActivity::class.java)
                intent.putExtra("memberId", data.id)
                if(type == "default") {
                    intent.putExtra("boardId", data.defaultBoardId)
                    intent.putExtra("boardType", "default")

                }else {
                    // todo : 사용자 게시판 정보 보내기
                }
                startActivity(intent)
            }

        })
    }
}