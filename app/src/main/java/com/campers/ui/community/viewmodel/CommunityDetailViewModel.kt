package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.home.HotCommunityDetailResult
import com.campers.repository.home.HomeRepository
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommunityDetailViewModel: ViewModel() {

    val hotCommunityDetailData: MutableLiveData<HotCommunityDetailResult> = MutableLiveData()

    /**
     * 핫 게시글 상세
     */
    fun getHotCommunityDetailData(type: Int, id: Int){
         CoroutineScope(Dispatchers.IO).launch {
             try {
                 hotCommunityDetailData.postValue(
                     HotCommunityDetailResult(success = HomeRepository().getHotCommunityDetailData(type, id))
                 )
             } catch (e: Exception) {
                 hotCommunityDetailData.postValue(
                     HotCommunityDetailResult(failure = e)
                 )
             }
         }
    }
}