package com.ssafy.heritage

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityTestBinding
import com.ssafy.heritage.viewmodel.TestViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

private const val TAG = "TestActivity___"
class TestActivity() : BaseActivity<ActivityTestBinding>(R.layout.activity_test) {

    private lateinit var imgg: MultipartBody.Part
    private var file: File? = null
    private val testViewModel by viewModels<TestViewModel>()
    private var state: Boolean = false
    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                this@TestActivity,
                "권한이 없습니다.\n$deniedPermissions",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun init() {
        initObserver()
        binding.apply {

            btnGallery.setOnClickListener {
                getProFileImage()
            }

            btnOk.setOnClickListener {
                if(state){
                    file?.let { it1 -> testViewModel.saveImage(it1) }
                }else{
                    Log.d(TAG, "STATE ${state}")
                }
            }
        }
    }

    private fun initObserver() {
        testViewModel.reviewState.observe(this) {
            Toast.makeText(this, "전송완료", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun getProFileImage() {
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check()
        Log.d(ContentValues.TAG, "사진변경 호출")
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "사용할 앱을 선택해주세요.")
        launcher.launch(chooserIntent)
    }

    // 절대경로 변환
    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                state = true
                val imagePath = result.data!!.data
                binding.btnGallery.setImageURI(imagePath)
                file = File(absolutelyPath(imagePath, this))
                val requestFile =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
                imgg = MultipartBody.Part.createFormData("file", file!!.name, requestFile)
        }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}