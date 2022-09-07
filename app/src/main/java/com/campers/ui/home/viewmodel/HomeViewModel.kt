package com.campers.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.home.HotCommunityListResult
import com.campers.data.home.RankingListResult
import com.campers.data.mypage.ProfileResult
import com.campers.repository.home.HomeRepository
import com.campers.repository.mypage.MypageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val rankingData: MutableLiveData<RankingListResult> = MutableLiveData()
    val hotCommunityData: MutableLiveData<HotCommunityListResult> = MutableLiveData()
    val profileData: MutableLiveData<ProfileResult> = MutableLiveData()

    /**
     * 랭킹 리스트
     */
    fun getRankingList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                rankingData.postValue(
                    RankingListResult(success = HomeRepository().getRankingListData())
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

    /**
     * 프로필 불러오기.
     * - 이름 사용, 로그인 했을 때 저장하면 편할듯
     */
    fun getProfileData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                profileData.postValue(
                    ProfileResult(success = MypageRepository().getProfileData(context))
                )
            }catch (e: Exception){
                profileData.postValue(
                    ProfileResult(failure = e)
                )
            }
        }
    }
}