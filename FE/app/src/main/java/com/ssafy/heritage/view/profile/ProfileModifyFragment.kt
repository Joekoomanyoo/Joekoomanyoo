package com.ssafy.heritage.view.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileModifyBinding
import com.ssafy.heritage.util.FileUtil
import com.ssafy.heritage.util.FormDataUtil
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "ProfileModifyFragment___"
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class ProfileModifyFragment :
    BaseFragment<FragmentProfileModifyBinding>(R.layout.fragment_profile_modify) {

    //    private val profileViewModel by viewModels<ProfileViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    lateinit var oldNickname: String
    var img_multipart: MultipartBody.Part? = null

    override fun init() {

        initObserver()

        initClickListener()

        setTextChangedListener()

        setItemSelectedListener()
    }


    @SuppressLint("LongLogTag")
    private fun initObserver() = with(binding) {
        userViewModel.user.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver: $it")
            binding.user = it.copy()

            oldNickname = it.userNickname
            spinnerYear.text = it.userBirth
            spinnerGender.text = when (it.userGender) {
                'M' -> "남자"
                else -> "여자"
            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun initClickListener() = with(binding) {

        // 프사 변경 버튼 클릭시
        btnChangeProfile.setOnClickListener {

            if (!hasPermissions()) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                pick()
            }
        }

        // 수정하기 버튼 클릭시
        btnModify.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {

                // 닉네임 중복검사 확인
                if (!userViewModel.nicknameVerify(
                        user!!.userNickname,
                        oldNickname,
                        tilNickname
                    )!!
                ) {
                    return@launch
                }
                Log.d(TAG, "닉네임 중복검사 확인 통과")
                // 비밀번호 유효성 검사 확인
                if (!userViewModel.validatePw(pw, pwCheck, tilPw, tilPwCheck)) {
                    return@launch
                }
                Log.d(TAG, "비밀번호 유효성 검사 확인")

                // 유효성 검사 통과하면 회원정보 수정
                // 먼저 파일 서버로 사진 보냄
                if (img_multipart == null || img_multipart?.let { userViewModel.sendImage(it) } == true) {
                    if (userViewModel.modify(user!!, pw) == true) {
                        // 수정완료후 마이페이지로 이동
                        makeToast("회원정보 수정 성공")
                        findNavController().navigate(R.id.action_profileModifyFragment_to_profileFragment)
                    } else {
                        makeToast("회원정보 수정에 실패하였습니다")
                    }
                } else {
                    makeToast("사진 등록에 실패했습니다")
                }
            }
        }
    }

    // 사진 선택
    private fun pick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActivityLauncher.launch(intent)
    }

    // 사진 골라서 가져온 결과
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {

                val imagePath = it.data!!.data
                Log.d(TAG, "imagePath: $imagePath")
                binding.ivProfile.setImageURI(imagePath)
                val file = FileUtil.toFile(requireContext(), imagePath!!)
                img_multipart = FormDataUtil.getImageBody("file", file)

            } else if (it.resultCode == RESULT_CANCELED) {
                makeToast("사진 선택 취소")
            } else {
                Log.d("ActivityResult", "something wrong")
            }
        }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            pick()
        } else {
            // PERMISSION NOT GRANTED
            makeToast("권한이 거부됨")
        }
    }


    private fun setTextChangedListener() = with(binding) {

        // 닉네임 입력창 에러 비활성화
        tilNickname.editText?.addTextChangedListener {
            tilNickname.isErrorEnabled = false
        }

        // 비밀번호 입력창 에러 비활성화
        tilPw.editText?.addTextChangedListener {
            tilPw.isErrorEnabled = false
        }

        // 비밀번호 재확인 입력창 에러 비활성화
        tilPwCheck.editText?.addTextChangedListener {
            tilPwCheck.isErrorEnabled = false
        }
    }

    @SuppressLint("LongLogTag")
    private fun setItemSelectedListener() = with(binding) {

        // 스피너 선택 리스너 설정
        spinnerYear.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            user?.userBirth = newItem
            spinnerYear.text = newItem
        }

        spinnerGender.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            when (newItem) {
                "남자" -> {
                    user?.userGender = 'M'
                    spinnerGender.text = "남자"
                }
                "여자" -> {
                    user?.userGender = 'F'
                    spinnerGender.text = "여자"
                }
            }
        }
    }

    // 권한 있는지 체크
    fun hasPermissions() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }


    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}