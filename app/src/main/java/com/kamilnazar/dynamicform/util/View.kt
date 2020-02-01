package com.kamilnazar.dynamicform.util

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt


fun Int.dpToPx(): Int {
    val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val px: Float = this * (metrics.densityDpi / 160f)
    return px.roundToInt()
}