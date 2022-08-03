package com.ssafy.heritage.base

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ssafy.heritage.R


abstract class BaseFragment<T: ViewDataBinding>(
    @LayoutRes val layoutResId: Int
) : Fragment() {

    var _binding: T? = null
    protected val binding get() = _binding!!
    // private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    protected abstract fun init()

    // protected fun loadingStart(){}

    // protected fun loadingEnd(){}

    override fun onDestroy() {
        super.onDestroy()
    //    loadingEnd()
        _binding = null
    }
}