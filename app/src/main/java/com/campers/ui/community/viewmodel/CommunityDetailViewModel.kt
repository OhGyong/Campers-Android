package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.community.DefaultBoardDetailResult
import com.campers.data.home.HotCommunityDetailResult
import com.campers.repository.community.CommunityRepository
import com.campers.repository.home.HomeRepository
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommunityDetailViewModel: ViewModel() {

    val hotCommunityDetailData: MutableLiveData<HotCommunityDetailResult> = MutableLiveData()
    val communityDefaultDetailData: MutableLiveData<DefaultBoardDetailResult> = MutableLiveData()

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

    /**
     * 기본 게시물 상세
     */
    fun getCommunityDefaultDetailData(defaultBoardContentsId: Int, memberId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityDefaultDetailData.postValue(
                    DefaultBoardDetailResult(success = CommunityRepository().getCommunityDefaultDetailData(defaultBoardContentsId, memberId))
                )
            } catch (e: Exception) {
                communityDefaultDetailData.postValue(
                    DefaultBoardDetailResult(failure = e)
                )
            }
        }
    }
}