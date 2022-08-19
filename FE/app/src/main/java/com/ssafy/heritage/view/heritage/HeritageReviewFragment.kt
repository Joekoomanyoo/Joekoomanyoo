package com.ssafy.heritage.view.heritage

import android.Manifest
import android.app.Activity
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
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageReviewAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.request.HeritageReviewRequest
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import com.ssafy.heritage.databinding.FragmentHeritageReviewBinding
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "HeritageReviewFragment___"
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class HeritageReviewFragment :
    BaseFragment<FragmentHeritageReviewBinding>(R.layout.fragment_heritage_review),
    OnItemClickListener {

    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    private lateinit var heritageReview: HeritageReviewRequest
    private lateinit var heritageReviewAdapter: HeritageReviewAdapter
    private var userSeq: Int = 0
    private var heritageReviewRegistedAt: String = ""
    private var heritageReviewText: String = ""
    private var attachSeq: Int = 0
    private var userNickname: String = ""

//    var PICK_IMAGE_FROM_ALBUM = 0
//    var storage: ReviewImgAttach? = null
//    var photoUri: Uri? = null

    // 로그인 한 유저만 리뷰쓰기 영역이 보여야함!

    override fun init() {
//        Log.d("review","can you see review??")
//        heritageViewModel.getHeritageReviewList()
//
//        initAdapter()
//        initObserver()
//        initClickListener()
    }

    private fun initAdapter() {
//        heritageReviewAdapter = HeritageReviewAdapter(this)
        binding.recyclerviewReviewList.adapter = heritageReviewAdapter
    }

    private fun initObserver() {
        heritageViewModel.heritageReviewList.observe(viewLifecycleOwner) {
            heritageReviewAdapter.submitList(it)
        }

        heritageViewModel.insertHeritageReview.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "리뷰가 등록되지 않았습니다. 서버오류", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 사진 첨부 버튼 클릭 시
        btnImgAttach.setOnClickListener {

            if (!hasPermissions()) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                pick()
            }
        }

//        // Initiate storage
//        storage = ReviewImgAttach.getInstance()
//
//        // Open the album
//        var photoPickerIntent = Intent(Intent.ACTION_PICK)
//        photoPickerIntent.type = "image/*"
//        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
//
//        btnImgAttach.setOnClickListener {
//            // 파일 이름 만들기
//            var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//            var imageFileName = "IMAGE_" + timestamp + "_.png"
//
//            var storageRef = storage?.reference?.child("image")?.child(imageFileName)
//
//            // File Upload
//            when {
//                storageRef?.putFile(photoUri)?.addOnSuccessListener {
//                    Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
//                }
//            }


        // 리뷰 글쓰기 버튼 클릭 시
//        btnCreateReview.setOnClickListener {
//
//            // 키보드 버튼 내리기 (둘 다 어째서인지 하이드 성공하고 앱이 꺼진다)
//            // 1안
////            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
//            // 2안
////            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
//
//            heritageReviewText = etReviewContent.text.toString()
////            attachSeq = btnImgAttach.
//
//            when {
//                heritageReviewText == "" -> {
//                    Toast.makeText(context, "리뷰를 작성해보세요.", Toast.LENGTH_SHORT).show()
//                }
////                storageRef?.putFile(photoUri)?.addOnSuccessListener {
////                    Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
////                }
//            }
//
//            if (heritageReviewText != "") {
//                heritageReview = HeritageReviewRequest(
//                    userSeq = userViewModel.user.value?.userSeq!!,
//                    heritageSeq = heritageViewModel.heritage.value?.heritageSeq!!,
//                    heritageReviewText,
//                    heritageViewModel.insertHeritageReview.value,
//                    userNickname = userViewModel.user.value?.userNickname!!
//                )
//                heritageViewModel.insertHeritageReview(heritageReview)
//                etReviewContent.setText("")
//            }
//        }

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
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                var currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            binding.btnImgAttach.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                currentImageUri
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.btnImgAttach.setImageBitmap(bitmap)
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (it.resultCode == Activity.RESULT_CANCELED) {
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

    override fun onItemClick(position: Int) {
        // 리뷰 삭제 버튼 동작
        heritageViewModel.deleteHeritageReview(
            // adapter에서 position 번째 아이템을 가져온당
            heritageReviewAdapter.currentList.get(position).heritageReviewSeq,
            heritageReviewAdapter.currentList.get(position).heritageSeq
        )
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
//            // 결과값이 사진을 선택했을 때
//            if (requestCode == Activity.RESULT_OK) {
//                // 사진 선택 시 이미지 넘어오게 되는 부분
//                photoUri = data?.data
//            } else {
//                // 취소할 때 작동되는 부분
//                finish()
//            }
//        }
//    }

}