package com.kamilnazar.dynamicform.util

fun String.isNumeric(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}