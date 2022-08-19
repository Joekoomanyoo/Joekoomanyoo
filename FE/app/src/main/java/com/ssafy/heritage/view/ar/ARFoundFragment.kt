package com.ssafy.heritage.view.ar

import android.content.Context
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.StampCategoryListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.databinding.FragmentARFoundBinding
import com.ssafy.heritage.listener.CategoryListClickListener
import com.ssafy.heritage.viewmodel.ARViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "ARFoundFragment___"

class ARFoundFragment : BaseFragment<FragmentARFoundBinding>(R.layout.fragment_a_r_found) {

    private val userSeq = ApplicationClass.sharedPreferencesUtil.getUser()
    private val stampCategoryListAdapter: StampCategoryListAdapter by lazy { StampCategoryListAdapter() }
    private val userViewModel by activityViewModels<UserViewModel>()
    private val arViewModel by activityViewModels<ARViewModel>()

    private val alphaInAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(stampCategoryListAdapter).apply {
            setDuration(500)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    override fun init() {

        arViewModel.getAllStamp()

//        userViewModel.getMyStamp()

        // arViewModel.selectMyStampCategory(userSeq, categorySeq = )

//        arViewModel.getStampCategory()

        initAdapter()

        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerViewBookitem.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3)
            adapter = alphaInAnimationAdapter

            stampCategoryListAdapter.categoryListClickListener =
                object : CategoryListClickListener {
                    override fun onClick(position: Int, stampCategory: StampCategory) {
                        val data =
                            arViewModel.stampList?.value!!.filter { it.stampCategory == stampCategory.categoryName }
                        Log.d(TAG, "sssss: ${stampCategory}")
                        Log.d(TAG, "sssss: ${userViewModel.myStampList?.value!!}")
                        Log.d(TAG, "sssss: $data")
                        val cnt = "${stampCategory.myCnt} / ${stampCategory.categoryCnt}"

                        // 내가 찾은 것들 필터링
                        userViewModel.myStampList.value?.forEach { myStamp ->
                            data.find { it.stampSeq == myStamp.stampSeq }?.found = 'Y'
                        }

                        val sheet = ARFoundDetail(data, cnt, stampCategory.categoryName)
                        sheet.show(parentFragmentManager, "ARFoundDetail")
                    }
                }
        }
    }

    private fun initObserver() {
//        arViewModel.categoryList.observe(viewLifecycleOwner) { list ->
//            userViewModel.myStampList.value?.forEach { myStamp ->
//                list.find {
//                    it.categoryName == myStamp.stampCategory
//                }?.add()
//            }
//            Log.d(TAG, "initObserver: $list")
//            stampCategoryListAdapter.submitList(list)
//        }

//        arViewModel.stampList.observe(viewLifecycleOwner) {
//            // 내가 찾은 것들 필터링
//            userViewModel.myStampList.value?.forEach { myStamp ->
//                it.find { it.stampSeq == myStamp.stampSeq }?.found = 'Y'
//            }
//            bookListAdapter.submitList(it)
//        }
//        userViewModel
//        userViewModel.myStampList.observe(viewLifecycleOwner) {
//            userViewModel.myStampList.value?.forEach { myStamp ->
//                list.find {
//                    it.categoryName == myStamp.stampCategory
//                }?.add()
//            }
//            Log.d(TAG, "initObserver: $list")
//            stampCategoryListAdapter.submitList(list)
//        }

        userViewModel.myStampList.observe(viewLifecycleOwner) {
            val list = arViewModel.categoryList.value
            it.forEach { myStamp ->
                list?.find {
                    it.categoryName == myStamp.stampCategory
                }?.add()
            }
            Log.d(TAG, "StampStamp: $it")
            stampCategoryListAdapter.submitList(list)
        }
    }
}