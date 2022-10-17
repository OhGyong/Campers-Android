package com.campers.ui.community

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.CommonData.Companion.userId
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

    private var boardId = 0 // 게시판 id
    private var boardType = "" // 게시판 type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_community_list)


        topActionBarListener()
        observeLiveData()
        setAdapter()
        clickListener()
    }

    override fun onResume() {
        super.onResume()
        showCommunityList()
    }

    private fun topActionBarListener() {
        mBinding.topActionBar.ivBack.setOnClickListener { finish() }
        mBinding.topActionBar.tvTitle.text = intent.getStringExtra("boardTitle")
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

        /**
         * 사용자 게시판 게시물 목록
         */
        mViewModel.communityMemberListData.observe(this, Observer {
            hideLoading()

            if(it.failure != null) {
                println("사용자 게시판 게시물 목록 호출 에러")
                return@Observer
            }

            val success = it.success

            if(success?.payload == null) {
                println("사용자 게시판 게시물 목록 호출 에러")
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
        boardId = intent.getIntExtra("boardId", 99999)
        boardType = intent.getStringExtra("type").toString()

        if(boardId == 99999){
            // TODO : 에러 화면 표시
            return
        }

        showLoading(this)
        if(boardType == "default") {
            mViewModel.getCommunityDefaultList(boardId)
        }else {
            mViewModel.getCommunityMemberList(boardId)
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
                println(data)
                val intent = Intent(this@CommunityListActivity, CommunityDetailActivity::class.java)
                intent.putExtra("memberId", userId)
                if(boardType == "default") {
                    intent.putExtra("boardId", data.id) // 게시물 id
                    intent.putExtra("boardType", "default")

                }else {
                    intent.putExtra("boardId", data.id) // 게시물 id
                    intent.putExtra("boardType", "member")
                }
                startActivity(intent)
            }
        })

        mBinding.ivListAdd.setOnClickListener {
            val intent = Intent(this@CommunityListActivity, CommunityRegistActivity::class.java)
            if(boardType == "default") {
                intent.putExtra("boardId", boardId)
                intent.putExtra("boardType", "default")

            }else {
                intent.putExtra("boardId", boardId)
                intent.putExtra("boardType", "member")
            }
            startActivity(intent)
        }
    }
}