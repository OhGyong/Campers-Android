package com.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.campers.R
import com.campers.data.community.CommunityBoardData
import com.campers.databinding.FragmentCommunityBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.adapter.CommunityBoardAdapter
import com.campers.ui.community.viewmodel.CommunityViewModel

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

    override fun onResume() {
        super.onResume()

        println("Community onResume")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as BaseActivity).showLoading(requireContext())
        mViewModel.getCommunityDefault()
        mViewModel.getCommunityMember()

        setAdapter()
        observeLiveData()

        val communityBtn = view.findViewById<Button>(R.id.home_to_list)
        communityBtn.setOnClickListener {
            findNavController().navigate(R.id.communityListFragment)
        }
    }

    private fun observeLiveData() {
        /**
         * 기본 게시판 목록 15개
         */
        mViewModel.communityDefaultData.observe(viewLifecycleOwner, Observer {
            println("!!!!")
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

            for(i in 0 until communityDefaultJson.size()){
                val payloadIndex = communityDefaultJson.get(i)
                communityDefaultList.add(
                    CommunityBoardData
                        (payloadIndex.asJsonObject.get("id").asInt,
                        payloadIndex.asJsonObject.get("name").toString().trim('"')
                    )
                )
            }

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

            for(i in 0 until communityMemberJson.size()){
                val payloadIndex = communityMemberJson.get(i)
                communityMemberList.add(
                    CommunityBoardData
                        (payloadIndex.asJsonObject.get("id").asInt,
                        payloadIndex.asJsonObject.get("name").toString().trim('"')
                    )
                )
            }

            mCommunityMemberAdapter.setData(communityMemberList)
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