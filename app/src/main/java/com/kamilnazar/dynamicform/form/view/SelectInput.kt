package com.kamilnazar.dynamicform.form.view

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.kamilnazar.dynamicform.form.serializer.FormField


class SelectInput(private val formField: FormField, private val context: Context) :
    BaseFromFieldView<String, Spinner>(formField, context) {
    override fun validate(): Boolean {
        if (formField.required && inputWidget.selectedItemPosition == -1) {
            showError("Required")
            return false
        }
        clearError()
        return true
    }

    override fun showError(message: String) {
        val selectedView = inputWidget.selectedView
        if (selectedView != null && selectedView is TextView) {
            selectedView.error = ""
            selectedView.text = message // actual error message
        }
    }

    override fun clearError() {
        val selectedView = inputWidget.selectedView
        if (selectedView != null && selectedView is TextView) {
            selectedView.error = null
        }
    }

    override fun getCleanedData(): String? {
        return if (validate()) {
            val index = inputWidget.selectedItemPosition
            if (index > -1 && index < formField.options.size)
                formField.options[index]
            else null
        } else null
    }

    override fun generateInputWidget(): Spinner {
        return Spinner(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            adapter = ArrayAdapter<String>(
                this@SelectInput.context,
                android.R.layout.simple_spinner_item,
                formField.options
            )
        }
    }

    override fun formData(): Pair<String, Any?> {
        return formField.fieldName to getCleanedData()
    }
}