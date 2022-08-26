package com.campers.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.home.HotCommunityListResponse
import com.campers.data.home.RankingListResponse
import com.campers.data.home.RankingListResult
import com.campers.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val rankingData: MutableLiveData<RankingListResult> = MutableLiveData()
    val hotCommunityData: MutableLiveData<HotCommunityListResponse> = MutableLiveData()

    // 랭킹 리스트
    fun getRankingList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                rankingData.postValue(
                    RankingListResult(success = HomeRepository().getRankingData())
                )
            } catch (e: Exception) {
                rankingData.postValue(
                    RankingListResult(failure = e)
                )
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