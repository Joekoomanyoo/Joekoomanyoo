package com.ssafy.heritage.view.heritage

import android.Manifest
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageListBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.viewmodel.HeritageViewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


private const val TAG = "HeritageListFragment___"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HeritageListFragment :
    BaseFragment<FragmentHeritageListBinding>(R.layout.fragment_heritage_list) {

    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }
    private val heritageViewModel by activityViewModels<HeritageViewModel>()

    override fun init() {

        initAdapter()

        initObserver()

        getHashKey()

        // 지도 목록으로 가는 버튼
        binding.ivMap.setOnClickListener {
            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
        }
    }

    override fun onStart() {
        super.onStart()
        // 나중에 서버 연결하면 사용할거임
//        heritageViewModel.getHeritageList()
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                requireActivity().getPackageManager().getPackageInfo(
                    requireActivity().getPackageName(),
                    PackageManager.GET_SIGNATURES
                )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }


    private fun initAdapter() = with(binding) {
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = heritageAdapter

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {
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
        // 더미데이터 집어넣었음
        heritageViewModel.test(list)
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

// 더미데이터임
private val list = arrayListOf<Heritage>(
    Heritage(
        1,
        "서울 숭례문",
        "조선 태조 7년(1398)",
        "서울 중구 세종대로 40 (남대문로4가)",
        "유적건조물",
        "126.975312652739",
        "37.559975221378",
        "http://www.cha.go.kr/unisearch/images/national_treasure/2685609.jpg",
        "조선시대 한양도성의 정문으로",
        "",
        'Y',
        "국보",
        0,
        0
    ),
    Heritage(
        2,
        "서울 원각사지 십층석탑",
        "조선시대 초기 15세기",
        "서울 종로구 종로 99 (종로2가) / (지번)서울 종로구 종로2가 38-2번지 탑골공원",
        "유적건조물",
        "126.988207994364",
        "37.5715461695449",
        "http://www.cha.go.kr/unisearch/images/national_treasure/1611449.jpg",
        "원각사는 지금의 탑골공원 자리에 있었던 절로, 조선 세조 11년(1465)에 세웠다. 조선시대의 숭유억불정책 속에서도 중요한 사찰로 보호되어 오다가 1504년 연산군이 이 절을 ‘연방원(聯芳院)’이라는 이름의 기생집으로 만들어 승려들을 내보냄으로써 절은 없어지게 되었다.",
        "",
        'Y',
        "국보",
        0,
        0
    )
)