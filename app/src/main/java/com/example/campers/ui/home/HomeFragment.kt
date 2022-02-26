package com.example.campers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.R
import com.example.campers.data.home.HotCommunityList
import com.example.campers.data.home.RankingList
import com.example.campers.ui.home.adapter.HotCommunityAdapter
import com.example.campers.ui.home.adapter.RankingAdapter
import com.example.campers.ui.home.viewmodel.HomeViewModel

class HomeFragment: Fragment() {

    // 뷰모델
    private val viewModel: HomeViewModel by viewModels()

    // 리사이클러 뷰
    private lateinit var rankingRecyclerView: RecyclerView
    private lateinit var hotCommunityRecyclerView: RecyclerView

    // observe에서 랭킹 데이터 리스트
    private lateinit var rankList: ArrayList<RankingList>
    private lateinit var hotCommunityList: ArrayList<HotCommunityList>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /**
         * 홈 화면의 랭킹 리스트를 관측
         */
        viewModel.rankingData.observe(viewLifecycleOwner, {
            val payload = it.payload

            /**
             *  비어있는 array에 add가 불가능하기 때문에
             *  arrayListOf로 데이터를 하나 추가한 뒤에 add로 데이터를 삽입한다.
             */
            for(i in 0 until 1){
                val payloadIndex = payload.get(i)
                val id = payloadIndex.asJsonObject.get("id").asInt
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"') // 따옴표 지우기
                val rank = payloadIndex.asJsonObject.get("rank").asInt
                val totalFire = payloadIndex.asJsonObject.get("totalFire").asInt
                rankList = arrayListOf(
                    RankingList(id, nickName, rank, totalFire)
                )
            }
            for (i in 1 until payload.size()) {
                val payloadIndex = payload.get(i)
                val id = payloadIndex.asJsonObject.get("id").asInt
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"')
                val rank = payloadIndex.asJsonObject.get("rank").asInt
                val totalFire = payloadIndex.asJsonObject.get("totalFire").asInt
                rankList.add(RankingList(id, nickName, rank, totalFire))
            }
            rankingRecyclerView = view.findViewById(R.id.home_ranking_recyclerView)
            rankingRecyclerView.adapter = RankingAdapter(rankList)
        })

        /**
         * 홈 화면의 핫한 게시글 리스트 관측
         */
        viewModel.hotCommunityData.observe(viewLifecycleOwner, {
            val payload = it.payload
            for(i in 0 until 1){
                val payloadIndex = payload.get(i)
                val type = payloadIndex.asJsonObject.get("type").asInt
                val id = payloadIndex.asJsonObject.get("id").asInt
                val title = payloadIndex.asJsonObject.get("title").toString().trim('"')
                val date = payloadIndex.asJsonObject.get("date").toString().trim('"')
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"') // 따옴표 지우기
                hotCommunityList = arrayListOf(
                    HotCommunityList(type, id, title, date, nickName)
                )
            }
            for (i in 1 until payload.size()) {
                val payloadIndex = payload.get(i)
                val type = payloadIndex.asJsonObject.get("type").asInt
                val id = payloadIndex.asJsonObject.get("id").asInt
                val title = payloadIndex.asJsonObject.get("title").toString().trim('"')
                val date = payloadIndex.asJsonObject.get("date").toString().trim('"')
                val nickName = payloadIndex.asJsonObject.get("nickName").toString().trim('"') // 따옴표 지우기
                hotCommunityList.add(HotCommunityList(type, id, title, date, nickName))
            }
            hotCommunityRecyclerView = view.findViewById(R.id.home_hotcommunity_recyclerView)
            hotCommunityRecyclerView.adapter = HotCommunityAdapter(hotCommunityList)
        })

    }

    override fun onResume() {
        super.onResume()
        println("Fragment 데이터 호출")
        viewModel.getRanking()
        viewModel.getHotCommunityList()
    }
}