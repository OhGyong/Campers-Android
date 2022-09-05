package com.campers.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.campers.R
import com.campers.databinding.FragmentCommunityBinding

class CommunityFragment: Fragment() {

    // 데이터 바인딩
    private lateinit var mBinding: FragmentCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)
        return mBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val communityBtn = view.findViewById<Button>(R.id.home_to_list)
        communityBtn.setOnClickListener {
            findNavController().navigate(R.id.communityListFragment)
        }
    }
}