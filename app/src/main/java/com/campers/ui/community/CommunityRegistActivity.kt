package com.campers.ui.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.campers.data.CommonData.Companion.boardContentId
import com.campers.data.CommonData.Companion.boardId
import com.campers.data.CommonData.Companion.boardType
import com.campers.data.CommonData.Companion.myId
import com.campers.data.CommonData.Companion.myNickName
import com.campers.data.community.CommunityContentRegistRequest
import com.campers.databinding.ActivityCommunityRegistBinding
import com.campers.ui.BaseActivity
import com.campers.ui.community.viewmodel.CommunityRegistViewModel
import org.json.JSONObject

class CommunityRegistActivity : BaseActivity() {

    private lateinit var mBinding: ActivityCommunityRegistBinding
    private val mViewModel : CommunityRegistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommunityRegistBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // reachEditor placeholder 설정
        mBinding.richEditor.setPlaceholder("내용을 입력하세요")

        topActionBarListener()
        clickListener()
        observeLiveData()
    }

    private fun observeLiveData() {
        /**
         * 사용자 게시물 글쓰기
         */
        mViewModel.communityMemberContentRegistData.observe(this, Observer{
            hideLoading()

            if(it.failure != null) {
                println("사용자 게시판 게시물 글쓰기 호출 에러")
                println(it.failure)
                return@Observer
            }

            if(it.success?.status == 400) {
                println("사용자 게시판 게시물 글쓰기 호출 에러")
                println(it.success)
                return@Observer
            }

            boardContentId = it.success?.payload?.get("insertId")?.asInt!!
            boardType = "member"
            val intent = Intent(this@CommunityRegistActivity, CommunityDetailActivity::class.java)
            startActivity(intent)
            finish()

        })
    }

    private fun topActionBarListener() {
        mBinding.topActionBar.ivBack.setOnClickListener { finish() }
    }

    private fun clickListener() {
        // 키보드 내리기
        mBinding.communityRegistLayout.setOnClickListener {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        // 이미지 추가
        mBinding.ibImgAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryRegisterResult.launch(intent)
        }

        // 완료 버튼
        mBinding.btCommunityRegistComplete.setOnClickListener {
            showLoading(this)

            if(boardId == 0){
                // TODO : 에러 처리
                return@setOnClickListener
            }

            val request = CommunityContentRegistRequest(
                mBinding.etTitleRegist.text.toString(),
                myNickName,
                JSONObject().put("contents", mBinding.richEditor.html),
                boardId,
                null,
                myId
            )

            // 기본 게시판 게시물 글쓰기
            if(boardType == "default") {
                // TODO : 추후 작업
            }
            // 사용자 게시판 게시물 글쓰기
            else {
                mViewModel.getCommunityMemberContentRegist(request)
            }
        }
    }

    /**
     * 갤러리 이미지
     */
    private val galleryRegisterResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val imageIntent = it.data
            try {
                mBinding.richEditor.insertImage(imageIntent?.dataString, "", 300, 150)
            } catch (error: Error) {
                println("갤러리 이미지 불러오기 에러")
            }
        }
}