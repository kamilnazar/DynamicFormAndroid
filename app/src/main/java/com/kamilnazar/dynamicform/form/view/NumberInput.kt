package com.kamilnazar.dynamicform.form.view

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.kamilnazar.dynamicform.form.serializer.FormField
import com.kamilnazar.dynamicform.util.isNumeric

class NumberInput(private val formField: FormField, private val context: Context) :
    BaseFromFieldView<Number, TextView>(formField, context) {
    override fun validate(): Boolean {
        val input = inputWidget.text.toString()
        if (formField.required && input.isBlank()) {
            showError("Required")
            return false
        }
        if (input.isNotBlank() && !input.isNumeric()) {
            showError("Not a number")
            return false
        }
        if (input.isNotBlank() && formField.min != null && input.toInt() < formField.min) {
            showError("Cannot be less than ${formField.min}")
            return false
        }
        if (input.isNotBlank() && formField.max != null && input.toInt() > formField.max) {
            showError("Cannot be greater than ${formField.max}")
            return false
        }
        return true
    }

    override fun showError(message: String) {
        inputWidget.error = message
    }

    override fun clearError() {
        inputWidget.error = null
    }

    override fun getCleanedData(): Number? {
        return if (validate() && inputWidget.text.toString().isNumeric())
            inputWidget.text.toString().toInt()
        else null
    }

    override fun generateInputWidget(): TextView {
        return EditText(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            inputType = EditorInfo.TYPE_CLASS_NUMBER
        }
    }

    override fun formData(): Pair<String, Any?> {
        return formField.fieldName to getCleanedData()
    }
}