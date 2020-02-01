package com.kamilnazar.dynamicform.form.view

import android.view.View

abstract class BaseView {
    abstract val view: View
    abstract fun validate(): Boolean
    abstract fun showError(message: String)
    protected abstract fun clearError()
    abstract fun formData(): Pair<String, Any?>
}