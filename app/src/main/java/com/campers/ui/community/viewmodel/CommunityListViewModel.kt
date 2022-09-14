package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.community.CommunityListResult
import com.campers.repository.community.CommunityRepository
import com.campers.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityListViewModel: ViewModel() {
    val communityDefaultListData: MutableLiveData<CommunityListResult> = MutableLiveData()

    /**
     * 기본 게시판 게시물 목록
     */
    fun getCommunityDefaultList(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityDefaultListData.postValue(
                    CommunityListResult(success = CommunityRepository().getCommunityDefaultListData(id))
                )
            } catch (e: Exception) {
                communityDefaultListData.postValue(
                    CommunityListResult(failure = e)
                )
            }
        }
    }
}