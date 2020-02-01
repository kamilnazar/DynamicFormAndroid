package com.kamilnazar.dynamicform.form.view

import android.content.Context
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import com.kamilnazar.dynamicform.form.serializer.FormField

class TextInput(private val formField: FormField, private val context: Context) :
    BaseFromFieldView<String, EditText>(formField, context) {
    override fun validate(): Boolean {
        if (formField.required && inputWidget.text.toString().isBlank()) {
            showError("Required")
            return false
        }
        clearError()
        return true
    }

    override fun showError(message: String) {
        inputWidget.error = message
    }

    override fun clearError() {
        inputWidget.error = null
    }

    override fun getCleanedData(): String? {
        return if (validate() && inputWidget.text.toString().isNotBlank())
            inputWidget.text.toString()
        else null
    }

    override fun generateInputWidget(): EditText {
        return EditText(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (formField.type == "multiline") {
                inputType = EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
                minLines = 5
                isSingleLine = false
                imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                gravity = Gravity.TOP or Gravity.START
            }
        }
    }

    override fun formData(): Pair<String, Any?> {
        return formField.fieldName to getCleanedData()
    }
}