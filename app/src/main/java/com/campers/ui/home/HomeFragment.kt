package com.campers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.campers.R
import com.campers.data.home.HotCommunityList
import com.campers.data.home.RankingList
import com.campers.databinding.ActivityLoginBinding
import com.campers.databinding.ActivityMainBinding
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
    private var rankList: ArrayList<RankingList> = arrayListOf()
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

        viewModel.getRanking()
        viewModel.getHotCommunityList()

        observeLiveData()
    }

    private fun observeLiveData() {
        /**
         * 홈 화면의 랭킹 리스트를 관측
         */
        viewModel.rankingData.observe(viewLifecycleOwner, Observer{
            val payload = it.payload

            for (i in 0 until payload.size()) {
                val payloadIndex = payload.get(i)
                val id = payloadIndex.asJsonObject.get("id").asInt
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"')
                val rank = payloadIndex.asJsonObject.get("rank").asInt
                val totalFire = payloadIndex.asJsonObject.get("totalFire").asInt
                rankList.add(RankingList(id, nickName, rank, totalFire))
            }

            mBinding.homeRankingRecyclerView.adapter = RankingAdapter(rankList)
        })

        /**
         * 홈 화면의 핫한 게시글 리스트 관측
         */
        viewModel.hotCommunityData.observe(viewLifecycleOwner, Observer{
            val payload = it.payload

            for(i in 0 until payload.size()){
                val payloadIndex = payload.get(i)
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