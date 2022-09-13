package com.campers.ui.home

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
import com.campers.data.home.HotCommunityListData
import com.campers.data.home.RankingListData
import com.campers.data.mypage.ProfileData
import com.campers.databinding.FragmentHomeBinding
import com.campers.databinding.ItemListHomeHotcommunityBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.CommunityDetailActivity
import com.campers.ui.home.adapter.HotCommunityAdapter
import com.campers.ui.home.adapter.RankingAdapter
import com.campers.ui.home.viewmodel.HomeViewModel

class HomeFragment: Fragment() {

    // 뷰모델
    private val mViewModel: HomeViewModel by viewModels()

    // 데이터 바인딩
    private lateinit var mBinding: FragmentHomeBinding

    // observe에서 랭킹 데이터 리스트
    private var rankingList: ArrayList<RankingListData> = arrayListOf()
    private var hotCommunityList: ArrayList<HotCommunityListData> = arrayListOf()

    // 리사이클러 뷰 어댑터
    private lateinit var mRankingAdapter: RankingAdapter
    private lateinit var mHotCommunityAdapter: HotCommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as BaseActivity).showLoading(requireContext())
        mViewModel.getRankingList()
        mViewModel.getHotCommunityList()
        mViewModel.getProfileData(requireContext())

        setAdapter()
        clickListener()
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()

        println("HomeFragment onResume")
    }

    private fun observeLiveData() {
        /**
         * 회원 랭킹 리스트
         */
        mViewModel.rankingData.observe(viewLifecycleOwner, Observer{
            if(it.failure != null) {
                // TODO : 빈 랭킹 리스트 표시
                println("랭킹 리스트 호출 에러")
                return@Observer
            }

            val success = it.success

            if(success?.payload == null) {
                // TODO : 빈 랭킹 리스트 표시
                println("랭킹 리스트 호출 에러")
                return@Observer
            }

            val rankingJson = success.payload

            for (i in 0 until rankingJson.size()) {
                val payloadIndex = rankingJson.get(i)
                val id = payloadIndex.asJsonObject.get("id").asInt
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"')
                val rank = payloadIndex.asJsonObject.get("rank").asInt
                val totalFire = payloadIndex.asJsonObject.get("totalFire").asInt
                rankingList.add(RankingListData(id, nickName, rank, totalFire))
            }
            mRankingAdapter.setData(rankingList)
        })

        /**
         * 핫한 게시물 리스트
         */
        mViewModel.hotCommunityData.observe(viewLifecycleOwner, Observer{
            if(it.failure != null) {
                // TODO : 빈 랭킹 리스트 표시
                println("핫한 게시물 리스트 호출 에러")
                return@Observer
            }

            val success = it.success

            if(success?.payload == null) {
                // TODO : 빈 랭킹 리스트 표시
                println("핫한 게시물 리스트 호출 에러")
                return@Observer
            }

            val hotCommunityJson = success.payload

            for(i in 0 until hotCommunityJson.size()){
                val payloadIndex = hotCommunityJson.get(i)
                val type = payloadIndex.asJsonObject.get("type").asInt
                val id = payloadIndex.asJsonObject.get("id").asInt
                val title = payloadIndex.asJsonObject.get("title").toString().trim('"')
                val date = payloadIndex.asJsonObject.get("date").toString().trim('"')
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"') // 따옴표 지우기
                hotCommunityList.add(HotCommunityListData(type, id, title, date, nickName))
            }

            mHotCommunityAdapter.setList(hotCommunityList)
        })

        /**
         * 프로필 불러오기
         */
        mViewModel.profileData.observe(viewLifecycleOwner, Observer {
            (activity as BaseActivity).hideLoading()

            if(it.failure != null) {
                // TODO : 에러 팝업 표시?
                println("프로필 불러오기 실패")
                println(it.failure)
                return@Observer
            }

            val success= it.success

            if(success?.payload == null) {
                // TODO : 에러 팝업 표시?
                println("프로필 불러오기 실패")
                return@Observer
            }

            // "이름" 으로 결과를 받기 때문에 "을 제거
            var nickName = success.payload.get(0).asJsonObject.get("nickName").toString()
            nickName = nickName.substring((1 until nickName.length-1))
            mBinding.nickName = ProfileData(
                success.payload.get(0).asJsonObject.get("id").asInt,
                nickName,
                success.payload.get(0).asJsonObject.get("totalFire").asInt,
                success.payload.get(0).asJsonObject.get("rank").asInt
            )

            // xml에 결합한다고 주석함.
//            mBinding.homeUserName.text = nickName.substring((1 until nickName.length-1))
        })
    }

    private fun clickListener() {
        mHotCommunityAdapter.setOnItemClickListener(object : HotCommunityAdapter.OnItemClickListener{
            override fun setOnItemClick(
                binding: ItemListHomeHotcommunityBinding,
                data: HotCommunityListData
            ) {
                val intent = Intent(context, CommunityDetailActivity::class.java)
                intent.putExtra("type", data.type)
                intent.putExtra("id", data.id)
                intent.putExtra("isHot", true)
                startActivity(intent)
            }
        })
    }

    private fun setAdapter() {
        // 회원 랭킹
        mRankingAdapter = RankingAdapter()
        mBinding.homeRankingRecyclerView.adapter = mRankingAdapter

        // 핫 게시글
        mHotCommunityAdapter = HotCommunityAdapter()
        mBinding.homeHotcommunityRecyclerView.adapter = mHotCommunityAdapter
    }
}