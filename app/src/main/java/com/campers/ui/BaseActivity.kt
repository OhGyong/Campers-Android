package com.campers.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.campers.util.LoadingProgressBar

open class BaseActivity : AppCompatActivity() {

    var mLoadingProgressBar: LoadingProgressBar? = null

    fun showLoading(context: Context) {
        if(mLoadingProgressBar == null) {
            mLoadingProgressBar = LoadingProgressBar(context)
        }else {
            if(mLoadingProgressBar != null){
                if(mLoadingProgressBar!!.isShowing){
                    mLoadingProgressBar!!.cancel()
                    mLoadingProgressBar!!.dismiss()
                    mLoadingProgressBar = LoadingProgressBar(context)
                }
            }
        }
        mLoadingProgressBar!!.show()
    }

    fun hideLoading() {
        mLoadingProgressBar?.dismiss()
        mLoadingProgressBar?.cancel()
        mLoadingProgressBar = null
    }

}