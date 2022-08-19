package com.ssafy.heritage.view.group

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.EvaluationMemberListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.request.EvaluationRequest
import com.ssafy.heritage.databinding.FragmentEvaluationListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlin.properties.Delegates


private const val TAG = "EvaluationListFragment___"

class EvaluationListFragment :
    BaseFragment<FragmentEvaluationListBinding>(R.layout.fragment_evaluation_list),
    OnItemClickListener {
    private val args by navArgs<EvaluationListFragmentArgs>()
    private lateinit var evaluationMemberListAdapter: EvaluationMemberListAdapter
    private val groupViewModel by viewModels<GroupViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private var evaluationRequest = mutableListOf<EvaluationRequest>()
    private var receivedSeq by Delegates.notNull<Int>()
    override fun init() {
        Log.d(TAG, "init: ${args}")
        groupViewModel.selectGroupEvaluation(args.evalInfo.groupSeq, userSeq)
        binding.mainFrame.addPanelSlideListener(PanelEventListener())  // 이벤트 리스너 추가
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        evaluationMemberListAdapter = EvaluationMemberListAdapter(this)
        binding.recyclerviewEvaluationMemberList.adapter = evaluationMemberListAdapter
    }

    private fun initObserver() {
        groupViewModel.evalProfileList.observe(viewLifecycleOwner) {
            evaluationMemberListAdapter.submitList(it)
        }
    }

    private fun initClickListener() {
        binding.btnRequest.setOnClickListener {
            Log.d(TAG, "initClickListener: ${evaluationRequest}")
            groupViewModel.insertGroupEvaluation(evaluationRequest)
            findNavController().popBackStack()
        }
//        if(binding.btnEval1.isSelected){
//            binding.ivCheckEval1.visibility = View.VISIBLE
//        }else{
//            binding.ivCheckEval1.visibility = View.INVISIBLE
//        }
//        if(binding.btnEval2.isSelected){
//            binding.ivCheckEval2.visibility = View.VISIBLE
//        }else{
//            binding.ivCheckEval2.visibility = View.INVISIBLE
//        }
//        if(binding.btnEval3.isSelected){
//            binding.ivCheckEval3.visibility = View.VISIBLE
//        }else{
//            binding.ivCheckEval3.visibility = View.INVISIBLE
//        }
//        if(binding.btnEval4.isSelected){
//            binding.ivCheckEval4.visibility = View.VISIBLE
//        }else{
//            binding.ivCheckEval4.visibility = View.INVISIBLE
//        }
//        if(binding.btnEval5.isSelected){
//            binding.ivCheckEval5.visibility = View.VISIBLE
//        }else{
//            binding.ivCheckEval5.visibility = View.INVISIBLE
//        }

    }
    override fun onItemClick(position: Int) = with(binding) {

        val state = mainFrame.panelState
        mainFrame.isTouchEnabled = false

        if (btnEval1.isChecked) btnEval1.isChecked = false
        if (btnEval2.isChecked) btnEval2.isChecked = false
        if (btnEval3.isChecked) btnEval3.isChecked = false
        if (btnEval4.isChecked) btnEval4.isChecked = false
        if (btnEval5.isChecked) btnEval5.isChecked = false
        val item = evaluationMemberListAdapter.getItem(position)

        Log.d(TAG, "onItemClick: ${item.userNickname}")

        // 평가 당하는 사람의 정보
        receivedSeq = item.userSeq
        tvNickname.text = item.userNickname + "님"

        Glide.with(ivHeritageReviewUserImg.context)
            .load(item.profileImgUrl)
            .into(ivHeritageReviewUserImg)



        // 닫힌 상태일 경우 열기
        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            mainFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED

        }
        // 열린 상태일 경우 닫기
        else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mainFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        }
    }

    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {

        }

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {

            if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {

            } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {

//                binding.btnCompletion.setOnClickListener {
//                    groupViewModel.insertGroupEvaluation(erCheck)
//                }

                binding.btnCompletion.setOnClickListener {
                    val erCheck = EvaluationRequest(
                        userSeq,
                        receivedSeq,
                        args.evalInfo.groupSeq,
                        0,
                        if (binding.btnEval1.isChecked) 1 else 0,
                        if (binding.btnEval2.isChecked) 1 else 0,
                        if (binding.btnEval3.isChecked) 1 else 0,
                        if (binding.btnEval4.isChecked) 1 else 0,
                        if (binding.btnEval5.isChecked) 1 else 0
                    )
                    Log.d(TAG, "onPanelStateChanged: ${erCheck}")
                    evaluationRequest.add(erCheck)
                    binding.mainFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
                binding.btnCancellation.setOnClickListener{
                    // 평가 안할거냐는다이얼로그 필요
                    binding.mainFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    val erCheck = EvaluationRequest(
                        userSeq,
                        receivedSeq,
                        args.evalInfo.groupSeq,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                    )
                    evaluationRequest.add(erCheck)

                    binding.btnEval1.isChecked = false
                    binding.btnEval2.isChecked = false
                    binding.btnEval3.isChecked = false
                    binding.btnEval4.isChecked = false
                    binding.btnEval5.isChecked = false
                }

            }
        }
    }
}