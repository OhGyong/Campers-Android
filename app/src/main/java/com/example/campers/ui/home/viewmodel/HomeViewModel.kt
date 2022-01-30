package com.example.campers.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.campers.data.home.RankingResponse
import com.example.campers.repository.home.HomeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val rankingData: MutableLiveData<RankingResponse> = MutableLiveData()

    fun getRanking() {
        GlobalScope.launch {
            try {
                println("ViewModel 연결")
                val data = HomeRepository().getRankingData()
                rankingData.postValue(data)
            } catch (e: Error) {
                println("Ranking data get fail")
            }
        }
    }
}