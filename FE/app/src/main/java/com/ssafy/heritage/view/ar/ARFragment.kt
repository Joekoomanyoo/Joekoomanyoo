package com.ssafy.heritage.view.ar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.HelloGeoActivity
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.databinding.FragmentARBinding
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.dialog.ReconfirmDialog
import com.ssafy.heritage.viewmodel.ARViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "ARFragment___"

class ARFragment : BaseFragment<FragmentARBinding>(R.layout.fragment_a_r) {

    private val arViewModel by activityViewModels<ARViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {

        (requireActivity() as HomeActivity).setStatusbarColor("main")

        initClickListener()

        initObserver()

        initView()
    }

    override fun onStart() {
        super.onStart()
        arViewModel.getStampCategory()
        userViewModel.getMyStamp()
    }

    private fun initObserver() {
    }

    private fun initView() = with(binding) {
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(R.drawable.ad8))
            add(SlideModel(R.drawable.ad9))
        }
        imageSlide.setImageList(imageList)
    }

    private fun initClickListener() = with(binding) {

        // 뒤로가기
//        btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }

        // 도감보기
        btnFound.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("position", 1)
            }
            findNavController().navigate(R.id.action_ARFragment_to_ARInfoFragment, bundle)
        }

        // 순위보기
        btnList.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("position", 0)
            }
            findNavController().navigate(R.id.action_ARFragment_to_ARInfoFragment, bundle)
        }

        // 유물 찾기(카메라)
        btnPlay.setOnClickListener {
            val stampInfo: Stamp = ApplicationClass.sharedPreferencesUtil.getStamp()
            Log.d(TAG, "initClickListener: ${stampInfo}")
            if (stampInfo.heritageLat == "null" || stampInfo.heritageLng == "null" || stampInfo.found == 'Y') {
                val dialog = ReconfirmDialog(requireContext())
                dialog.show()
            } else {
                val intent = Intent(activity, HelloGeoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }

        }
    }
}