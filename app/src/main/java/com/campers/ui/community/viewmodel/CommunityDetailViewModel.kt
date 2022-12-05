package com.campers.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.api.CommonArrayResult
import com.campers.api.CommonObjectResult
import com.campers.data.community.BoardDetailResult
import com.campers.data.community.CommunityCommentRegistRequest
import com.campers.data.community.CommunityMemberContentFireRequest
import com.campers.data.home.HotCommunityDetailResult
import com.campers.repository.community.CommunityRepository
import com.campers.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityDetailViewModel: ViewModel() {

    val hotCommunityDetailData: MutableLiveData<HotCommunityDetailResult> = MutableLiveData()
    val communityDefaultDetailData: MutableLiveData<BoardDetailResult> = MutableLiveData()
    val communityMemberDetailData:  MutableLiveData<BoardDetailResult> = MutableLiveData()
    val communityMemberCommentData: MutableLiveData<CommonObjectResult> = MutableLiveData()
    val communityMemberContentFireData: MutableLiveData<CommonArrayResult> = MutableLiveData()

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
    fun getCommunityDefaultDetailData(defaultBoardContentsId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityDefaultDetailData.postValue(
                    BoardDetailResult(success = CommunityRepository().getCommunityDefaultDetailData(defaultBoardContentsId))
                )
            } catch (e: Exception) {
                communityDefaultDetailData.postValue(
                    BoardDetailResult(failure = e)
                )
            }
        }
    }

    /**
     * 사용자 게시물 상세
     */
    fun getCommunityMemberDetailData(memberBoardContentsId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityMemberDetailData.postValue(
                    BoardDetailResult(success = CommunityRepository().getCommunityMemberDetailData(memberBoardContentsId))
                )
            } catch (e: Exception) {
                communityMemberDetailData.postValue(
                    BoardDetailResult(failure = e)
                )
            }
        }
    }

    /**
     * 사용자 게시판 게시물 댓글 작성
     */
    fun getCommunityMemberCommentRegist(request : CommunityCommentRegistRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityMemberCommentData.postValue(
                    CommonObjectResult(success = CommunityRepository().getCommunityMemberCommentRegistData(request))
                )
            } catch (e: Exception) {
                communityMemberCommentData.postValue(
                    CommonObjectResult(failure = e)
                )
            }
        }
    }

    /**
     * 사용자 게시판 게시물 좋아요
     */
    fun getCommunityMemberContentFireData(request: CommunityMemberContentFireRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                communityMemberContentFireData.postValue(
                    CommonArrayResult(success = CommunityRepository().getCommunityContentFireData(request))
                )
            } catch (e: Exception) {
                communityMemberContentFireData.postValue(
                    CommonArrayResult(failure = e)
                )
            }
        }
    }
}