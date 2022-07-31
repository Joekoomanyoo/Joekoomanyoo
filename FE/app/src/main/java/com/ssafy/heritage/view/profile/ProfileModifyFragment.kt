package com.ssafy.heritage.view.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileModifyBinding
import com.ssafy.heritage.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProfileModifyFragment___"
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class ProfileModifyFragment :
    BaseFragment<FragmentProfileModifyBinding>(R.layout.fragment_profile_modify) {

    private val profileViewModel by viewModels<ProfileViewModel>()


    override fun init() {
        binding.profileVM = profileViewModel

        initView()

        initClickListener()

        setTextChangedListener()
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        val safeArgs: ProfileModifyFragmentArgs by navArgs()
        profileViewModel.user.value = safeArgs.user
        profileViewModel.oldNickname.value = safeArgs.user.userNickname
    }

    private fun initView() = with(binding) {
        val safeArgs: ProfileModifyFragmentArgs by navArgs()
        spinnerYear.text = safeArgs.user.userBirth
        spinnerGender.text = when (safeArgs.user.userGender) {
            'M' -> "남자"
            else -> "여자"
        }
    }

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
                if (!profileViewModel.nicknameVerify(tilNickname)) {
                    return@launch
                }

                // 비밀번호 유효성 검사 확인
                if (!profileViewModel.validatePw(tilPw, tilPwCheck)) {
                    return@launch
                }

                // 유효성 검사 통과하면 회원정보 수정
                makeToast("수정 완료")
                profileViewModel.modify()
                // 수정완료후 마이페이지로 이동
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
                var currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            binding.ivProfile.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.ivProfile.setImageBitmap(bitmap)
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
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