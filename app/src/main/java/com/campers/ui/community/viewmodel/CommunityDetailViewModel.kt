package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.repository.home.HomeRepository
import com.google.gson.JsonArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommunityDetailViewModel(): ViewModel() {

    val communityDetailData: MutableLiveData<JsonArray> = MutableLiveData()

     fun getCommunityDetailData(type: Int, id: Int){
         GlobalScope.launch {
             try {
                 val data = HomeRepository().getHotCommunityDetailData(type, id).payload
                 communityDetailData.postValue(data)
             } catch (err: Error) {
                 println("getHotCommunityDetailData 호출 실패")
             }
         }
    }
}