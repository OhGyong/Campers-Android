package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.api.CommonObjectResult
import com.campers.data.community.CommunityContentRegistRequest
import com.campers.repository.community.CommunityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityRegistViewModel : ViewModel() {
    val communityMemberContentRegistData: MutableLiveData<CommonObjectResult> = MutableLiveData()

    /**
     * 사용자 게시판 게시물 글쓰기
     */
    fun getCommunityMemberContentRegist(request: CommunityContentRegistRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityMemberContentRegistData.postValue(
                    CommonObjectResult(success = CommunityRepository().getCommunityMemberContentRegistData(request))
                )
            } catch (e: Exception) {
                communityMemberContentRegistData.postValue(
                    CommonObjectResult(failure = e)
                )
            }
        }
    }
}