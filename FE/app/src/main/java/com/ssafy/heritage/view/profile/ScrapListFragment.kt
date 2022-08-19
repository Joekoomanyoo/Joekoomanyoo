package com.ssafy.heritage.view.profile

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentScrapListBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.heritage.HeritageDetailFragment
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "ScrapListFragment___"

class ScrapListFragment : BaseFragment<FragmentScrapListBinding>(R.layout.fragment_scrap_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val userViewModel by activityViewModels<UserViewModel>()
    private val heritageViewModel by activityViewModels<HeritageViewModel>()

    override fun init() {

        (requireActivity() as HomeActivity).setStatusbarColor("main")

        initAdapter()

        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerviewScrap.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = heritageAdapter

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {

                    heritageViewModel.setHeritage(heritage)

                    // 해당 문화유산의 상세페이지로 이동
                    parentFragmentManager
                        .beginTransaction()
                        .addSharedElement(view, "heritage")
                        .addToBackStack(null)
                        .replace(
                            R.id.fragment_container_main,
                            HeritageDetailFragment.newInstance(heritage)
                        )
                        .commit()
                }
            }
        }
    }

    private fun initObserver() {
        userViewModel.scrapList.observe(viewLifecycleOwner) {
            heritageAdapter.submitList(it)
        }
    }
}