package com.ssafy.heritage.view

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.NotiListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentNotiBinding
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "NotiFragment"

class NotiFragment : BaseFragment<FragmentNotiBinding>(R.layout.fragment_noti) {

    private val notiListAdapter: NotiListAdapter by lazy { NotiListAdapter() }
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {

        initAdapter()

        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerView.apply {
            adapter = notiListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initObserver() {
        userViewModel.notiList.observe(viewLifecycleOwner) {
            notiListAdapter.submitList(it)
        }
    }

}