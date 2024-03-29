package com.campers.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.CommonData.Companion.boardContentId
import com.campers.data.CommonData.Companion.boardId
import com.campers.data.CommonData.Companion.boardType
import com.campers.data.community.CommunityBoardData
import com.campers.data.community.CommunityListData
import com.campers.databinding.FragmentCommunityBinding
import com.campers.databinding.ItemListCommunityBoardBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.adapter.CommunityBoardAdapter
import com.campers.ui.community.viewmodel.CommunityViewModel
import com.google.gson.Gson

class CommunityFragment: Fragment() {

    private lateinit var mBinding: FragmentCommunityBinding
    private val mViewModel: CommunityViewModel by viewModels()

    private lateinit var mCommunityDefaultAdapter: CommunityBoardAdapter
    private lateinit var mCommunityMemberAdapter: CommunityBoardAdapter

    private var communityDefaultList: ArrayList<CommunityBoardData> = arrayListOf()
    private var communityMemberList = arrayListOf<CommunityBoardData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as BaseActivity).showLoading(requireContext())
        mViewModel.getCommunityDefault()
        mViewModel.getCommunityMember()

        setAdapter()
        observeLiveData()
        clickListener()
    }

    private fun observeLiveData() {
        /**
         * 기본 게시판 목록 15개
         */
        mViewModel.communityDefaultData.observe(viewLifecycleOwner, Observer {
            (activity as BaseActivity).hideLoading()

            if(it.failure != null) {
                // TODO : 빈 처리를 어떻게 할까
                println("기본 게시판 목록 15개 호출 에러")
                return@Observer
            }

            val success = it.success

            if(success?.payload == null) {
                // TODO : 빈 처리를 어떻게 할까
                println("기본 게시판 목록 15개 없음")
                return@Observer
            }

            val communityDefaultJson = success.payload
            communityDefaultList = Gson()
                .fromJson(communityDefaultJson, Array<CommunityBoardData>::class.java)
                .toCollection(ArrayList())

            mCommunityDefaultAdapter.setData(communityDefaultList)
        })

        /**
         * 사용자 게시판 목록 15개
         */
        mViewModel.communityMemberData.observe(viewLifecycleOwner, Observer {
            (activity as BaseActivity).hideLoading()

            if(it.failure != null) {
                // TODO : 빈 처리를 어떻게 할까
                println("사용자 게시판 목록 15개 호출 에러")
                return@Observer
            }

            val success = it.success

            if(success?.payload == null) {
                // TODO : 빈 처리를 어떻게 할까
                println("사용자 게시판 목록 15개 없음")
                return@Observer
            }

            val communityMemberJson = success.payload
            communityMemberList = Gson()
                .fromJson(communityMemberJson, Array<CommunityBoardData>::class.java)
                .toCollection(ArrayList())

            mCommunityMemberAdapter.setData(communityMemberList)
        })
    }

    private fun clickListener() {
        mCommunityDefaultAdapter.setOnItemClickListener(object : CommunityBoardAdapter.OnItemClickListener{
            override fun setOnItemClick(
                binding: ItemListCommunityBoardBinding,
                data: CommunityBoardData
            ) {
                boardId = data.id // 기본 게시판 id
                boardType = "default" // 게시판 타입
                val intent = Intent(context, CommunityListActivity::class.java)
                intent.putExtra("boardTitle", data.name) // 게시판 타이틀
                startActivity(intent)
            }
        })

        mCommunityMemberAdapter.setOnItemClickListener(object : CommunityBoardAdapter.OnItemClickListener{
            override fun setOnItemClick(
                binding: ItemListCommunityBoardBinding,
                data: CommunityBoardData
            ) {
                boardId = data.id // 사용자 게시판 id
                boardType = "member" // 게시판 타입
                val intent = Intent(context, CommunityListActivity::class.java)
                intent.putExtra("boardTitle", data.name) // 게시판 타이틀
                startActivity(intent)
            }
        })

    }

    private fun setAdapter() {
        // 기본 게시판 15개
        mCommunityDefaultAdapter = CommunityBoardAdapter()
        mBinding.rvCommunityDefault.adapter = mCommunityDefaultAdapter

        // 사용자 게시판 15개
        mCommunityMemberAdapter = CommunityBoardAdapter()
        mBinding.rvCommunityMember.adapter = mCommunityMemberAdapter
    }
}