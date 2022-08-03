package com.ssafy.heritage.view.heritage

import android.Manifest
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageListBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.viewmodel.HeritageViewModel


private const val TAG = "HeritageListFragment___"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val heritageViewModel by activityViewModels<HeritageViewModel>()

    override fun init() {

        initAdapter()

        initObserver()

        // 지도 목록으로 가는 버튼
        binding.ivMap.setOnClickListener {
            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
        }
    }

    private fun initAdapter() = with(binding) {
        recyclerview.apply {
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
        heritageViewModel.heritageList.observe(viewLifecycleOwner) {
            heritageAdapter.submitList(it)
        }
    }

    // 위치 권한 체크 해주고 이후 동작 설정
    private val requestPermissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED
                findNavController().navigate(R.id.action_heritageListFragment_to_heritageMapFragment)
            } else {
                // PERMISSION NOT GRANTED
                makeToast("위치 권한이 필요합니다")
            }
        }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}