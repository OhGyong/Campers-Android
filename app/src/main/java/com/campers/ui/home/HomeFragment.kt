package com.campers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.campers.R
import com.campers.data.home.HotCommunityList
import com.campers.data.home.RankingList
import com.campers.databinding.FragmentHomeBinding
import com.campers.ui.home.adapter.HotCommunityAdapter
import com.campers.ui.home.adapter.RankingAdapter
import com.campers.ui.home.viewmodel.HomeViewModel

class HomeFragment: Fragment() {

    // 뷰모델
    private val viewModel: HomeViewModel by viewModels()

    // 데이터 바인딩
    private lateinit var mBinding: FragmentHomeBinding

    // observe에서 랭킹 데이터 리스트
    private var rankingList: ArrayList<RankingList> = arrayListOf()
    private var hotCommunityList: ArrayList<HotCommunityList> = arrayListOf()

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

        viewModel.getRankingList()
        viewModel.getHotCommunityList()

        observeLiveData()
    }

    private fun observeLiveData() {
        /**
         * 회원 랭킹 리스트
         */
        viewModel.rankingData.observe(viewLifecycleOwner, Observer{
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
                rankingList.add(RankingList(id, nickName, rank, totalFire))
            }

            mBinding.homeRankingRecyclerView.adapter = RankingAdapter(rankingList)
        })

        /**
         * 핫한 게시물 리스트
         */
        viewModel.hotCommunityData.observe(viewLifecycleOwner, Observer{
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
                hotCommunityList.add(HotCommunityList(type, id, title, date, nickName))
            }

            mBinding.homeHotcommunityRecyclerView.adapter = HotCommunityAdapter(hotCommunityList)
        })
    }
}