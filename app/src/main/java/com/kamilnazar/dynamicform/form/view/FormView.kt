package com.kamilnazar.dynamicform.form.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.kamilnazar.dynamicform.form.exception.FormTypeException
import com.kamilnazar.dynamicform.form.serializer.Form
import com.kamilnazar.dynamicform.form.serializer.FormField

class FormView(private val context: Context, private val form: Form) {
    private val childView: MutableList<BaseView> = mutableListOf()
    private var rootView: LinearLayout = LinearLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        orientation = LinearLayout.VERTICAL
    }

    fun generateView(): View {

        form.fields.asSequence()
            .map { formField -> getFormWidget(formField) }
            .forEach { formFieldView ->
                childView.add(formFieldView)
                rootView.addView(formFieldView.view)
            }

        return rootView
    }

    fun getFormData(): HashMap<String, Any?> {
        return childView.map { baseView -> baseView.formData() }.toMap() as HashMap<String, Any?>
    }

    fun checkAtleastOneFieldHasData(): Boolean {
        return childView.map { baseView -> baseView.formData() }.any { it.second != null }
    }

    fun validateAll(): Boolean {
        return childView.map { it.validate() }.all { it }
    }

    private fun getFormWidget(formField: FormField): BaseView {
        return when (formField.type) {
            "text" -> generateTextInput(formField)
            "number" -> generateNumberInput(formField)
            "multiline" -> generateTextInput(formField)
            "dropdown" -> generateSpinner(formField)
            "composite" -> generateCompositeField(formField)
            else -> throw FormTypeException(
                "Unknown form type ${formField.type} found for form field $formField"
            )
        }
    }

    private fun generateCompositeField(formField: FormField): BaseView {
        return CompositeField(formField, context)
    }

    private fun generateTextInput(formField: FormField) =
        TextInput(formField, context)

    private fun generateNumberInput(formField: FormField) =
        NumberInput(formField, context)

    private fun generateSpinner(formField: FormField) = SelectInput(formField, context)

    fun checkCompositeResult(requestCode: Int): CompositeField? {
        for (baseView in childView) {
            if (baseView is CompositeField && baseView.COMPOSITE_REQUEST == requestCode) {
                return baseView
            }
        }
        return null
    }
}