package com.ssafy.heritage.view.feed

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.FeedListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.databinding.FragmentFeedMyListBinding
import com.ssafy.heritage.listener.FeedListClickListener
import com.ssafy.heritage.viewmodel.FeedViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "FeedMyListFragment___"

class FeedMyListFragment : BaseFragment<FragmentFeedMyListBinding>(R.layout.fragment_feed_my_list) {

    private val feedAdapter by lazy { FeedListAdapter() }
    private val feedViewModel by activityViewModels<FeedViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    private val alphaInAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(feedAdapter).apply {
            setDuration(300)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    override fun init() {

        feedViewModel.getFeedListAll()
        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.recyclerviewFeedList.adapter = alphaInAnimationAdapter
        binding.recyclerviewFeedList.layoutManager = GridLayoutManager(requireContext(), 3)
        feedAdapter.feedListClickListener = object : FeedListClickListener {
            override fun onClick(position: Int, feed: FeedListResponse, view: View) {
                parentFragment!!.parentFragmentManager
                    .beginTransaction()
                    .addSharedElement(view, "feed")
                    .addToBackStack(null)
                    .replace(
                        R.id.fragment_container_main,
                        FeedDetailFragment.newInstance(feed)
                    )
                    .commit()
            }
        }
    }

    private fun initObserver() {
        feedViewModel.feedListAll.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver feedListAll: $it")

            val list = it.filter { it.userSeq == userViewModel.user.value?.userSeq!! }

            feedAdapter.submitList(list.sortedBy { it.createdTime }.reversed())

            // 뷰 다 불러오고나서 transition 효과 시작
            (view?.parent as ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

}