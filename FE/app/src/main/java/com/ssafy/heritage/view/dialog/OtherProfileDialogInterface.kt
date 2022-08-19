package com.ssafy.heritage.view.dialog

interface OtherProfileDialogInterface {
    fun onApproveBtnClicked(userSeq: Int)
    fun onRefuseBtnClicked(userSeq: Int)
    fun onDropBtnClicked(userSeq: Int)
}