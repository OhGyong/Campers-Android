package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.community.CommunityDefaultResult
import com.campers.repository.community.CommunityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityViewModel: ViewModel() {
    val communityDefaultData: MutableLiveData<CommunityDefaultResult> = MutableLiveData()

    /**
     * 기본 게시판 목록 15개
     */
    fun getCommunityDefault() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityDefaultData.postValue(
                    CommunityDefaultResult(success = CommunityRepository().getCommunityDefaultData())
                )
            }catch (e: Exception){
                communityDefaultData.postValue(
                    CommunityDefaultResult(failure = e)
                )
            }
        }
    }


}