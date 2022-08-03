package com.ssafy.heritage.view.heritage

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentHeritageReviewBinding
import com.ssafy.heritage.viewmodel.HeritageViewModel

class HeritageReviewFragment :
    BaseFragment<FragmentHeritageReviewBinding>(R.layout.fragment_heritage_review) {

    private val heritageViewModel by viewModels<HeritageViewModel>()

    override fun init() {
        Log.d("review","can you see review??")
        heritageViewModel.getHeritageList()
    }

}