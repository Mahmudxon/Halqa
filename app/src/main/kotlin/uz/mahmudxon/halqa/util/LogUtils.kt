package uz.mahmudxon.halqa.util

import android.util.Log

val Any.TAG : String
    get() = "HALQA@${this.javaClass.canonicalName?.split('.')?.last()}"

fun Any.logd(msg: String) = Log.d(TAG, msg)