package com.campers.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.home.HotCommunityListResponse
import com.campers.data.home.HotCommunityListResult
import com.campers.data.home.RankingListResponse
import com.campers.data.home.RankingListResult
import com.campers.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val rankingData: MutableLiveData<RankingListResult> = MutableLiveData()
    val hotCommunityData: MutableLiveData<HotCommunityListResult> = MutableLiveData()

    /**
     * 랭킹 리스트
     */
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

    /**
     * 핫 게시물 리스트
     */
    fun getHotCommunityList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                hotCommunityData.postValue(
                    HotCommunityListResult(success = HomeRepository().getHotCommunityListData())
                )
            } catch (e: Exception) {
                hotCommunityData.postValue(
                    HotCommunityListResult(failure = e)
                )
            }
        }
    }
}