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
import com.campers.data.community.CommunityDefaultData
import com.campers.databinding.FragmentCommunityBinding
import com.campers.ui.community.adapter.CommunityDefaultAdapter
import com.campers.ui.community.viewmodel.CommunityViewModel

class CommunityFragment: Fragment() {

    private lateinit var mBinding: FragmentCommunityBinding
    private val mViewModel: CommunityViewModel by viewModels()
    private lateinit var mCommunityDefaultAdapter: CommunityDefaultAdapter

    private var communityDefaultList = arrayListOf<CommunityDefaultData>()

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

        setAdapter()
        observeLiveData()

        mViewModel.getCommunityDefault()

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
                    CommunityDefaultData
                        (payloadIndex.asJsonObject.get("id").asInt,
                        payloadIndex.asJsonObject.get("name").toString().trim('"')
                    )
                )
            }

            mCommunityDefaultAdapter.setData(communityDefaultList)
        })
    }

    private fun setAdapter() {
        // 기본게시판 15개
        mCommunityDefaultAdapter = CommunityDefaultAdapter()
        mBinding.rvCommunityDefault.adapter = mCommunityDefaultAdapter

    }
}