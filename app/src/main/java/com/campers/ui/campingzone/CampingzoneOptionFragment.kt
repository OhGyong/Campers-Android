package com.campers.ui.campingzone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.campers.R

@Suppress("UNREACHABLE_CODE")
class CampingzoneOptionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_campingzone_option,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val optionImage = view.findViewById<ImageView>(R.id.option_image)
        optionImage?.setOnClickListener {
            findNavController().navigate(R.id.campingzoneListFragment)
        }
    }


}