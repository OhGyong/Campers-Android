package com.campers.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.home.HotCommunityListResponse
import com.campers.data.home.RankingListResponse
import com.campers.repository.home.HomeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val rankingData: MutableLiveData<RankingListResponse> = MutableLiveData()
    val hotCommunityData: MutableLiveData<HotCommunityListResponse> = MutableLiveData()

    fun getRanking() {
        GlobalScope.launch {
            try {
                val data = HomeRepository().getRankingData()
                rankingData.postValue(data)
            } catch (e: Error) {
                println("Ranking data get fail")
            }
        }
    }

    fun getHotCommunityList() {
        GlobalScope.launch {
            try {
                println("ViewModel 연결")
                val data = HomeRepository().getHotCommunityListData()
                hotCommunityData.postValue(data)
            } catch (e: Error) {
                println("HotCommunity data get fail")
            }
        }
    }
}