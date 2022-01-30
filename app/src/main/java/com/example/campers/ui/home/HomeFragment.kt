package com.example.campers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.R
import com.example.campers.data.home.Ranking
import com.example.campers.ui.home.adapter.RankingAdapter
import com.example.campers.ui.home.viewmodel.HomeViewModel

class HomeFragment: Fragment() {

    // 뷰모델
    private val viewModel: HomeViewModel by viewModels()

    // 리사이클러 뷰
    private lateinit var rankingRecyclerView: RecyclerView

    // observe에서 랭킹 데이터 리스트
    private lateinit var rankList: ArrayList<Ranking>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.rankingData.observe(viewLifecycleOwner, {
            println("데이터 observe")
            val payload = it.payload

            /**
             *  비어있는 array에 add가 불가능하기 때문에
             *  arrayListOf로 데이터를 하나 추가한 뒤에 add로 데이터를 삽입한다.
             */
            for(i in 0 until 1){
                val payloadIndex = payload.get(i)
                val id = payloadIndex.asJsonObject.get("id").asInt
                val nickName = payloadIndex.asJsonObject.get("nickName").toString()
                val rank = payloadIndex.asJsonObject.get("rank").asInt
                val totalFire = payloadIndex.asJsonObject.get("totalFire").asInt

                rankList = arrayListOf(
                    Ranking(id, nickName, rank, totalFire)
                )
            }
            for (i in 1 until payload.size()) {
                val payloadIndex = payload.get(i)
                val id = payloadIndex.asJsonObject.get("id").asInt
                val nickName = payloadIndex.asJsonObject.get("nickName").toString()
                val rank = payloadIndex.asJsonObject.get("rank").asInt
                val totalFire = payloadIndex.asJsonObject.get("totalFire").asInt
                rankList.add(Ranking(id, nickName, rank, totalFire))
            }
            println(rankList)
            rankingRecyclerView = view.findViewById(R.id.ranking_recyclerView)
            rankingRecyclerView.adapter = RankingAdapter(rankList)
        })
    }

    override fun onResume() {
        super.onResume()
        println("Fragment 데이터 호출")
        viewModel.getRanking()
    }
}