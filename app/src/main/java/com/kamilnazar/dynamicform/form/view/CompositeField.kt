package com.kamilnazar.dynamicform.form.view

import android.content.Context
import android.widget.Button
import android.widget.LinearLayout
import com.kamilnazar.dynamicform.dynamicform.DynamicFormActivity
import com.kamilnazar.dynamicform.form.serializer.Form
import com.kamilnazar.dynamicform.form.serializer.FormField
import com.squareup.moshi.Moshi
import kotlin.math.roundToInt

class CompositeField(private val formField: FormField, private val context: Context) :
    BaseFromFieldView<HashMap<String, Any?>, Button>(formField, context) {
    val COMPOSITE_REQUEST by lazy { (Math.random() * 1000).roundToInt() }
    private var formData: HashMap<String, Any?>? = null
    override fun getCleanedData(): HashMap<String, Any?>? {
        return formData
    }

    override fun generateInputWidget(): Button {
//        val container = LinearLayout(context)
//            .apply {
//                layoutParams = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT)
//            }
//        container
        val button = Button(context).apply {
            text = formField.fieldName
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                val moshi = Moshi.Builder().build()
                val formAdapter = moshi.adapter<Form>(
                    Form::class.java
                )
                val fields = formField.fields
                val form = Form(fields = fields)
                val string = formAdapter.toJson(form)
                DynamicFormActivity.start(
                    this@CompositeField.context as DynamicFormActivity,
                    COMPOSITE_REQUEST, string
                )
            }
        }
        return button
    }

    override fun validate(): Boolean {
        return true
    }

    override fun showError(message: String) {
    }

    override fun clearError() {
    }

    public fun setFormData(f: HashMap<String, Any?>) {
        this.formData = f
    }

    override fun formData(): Pair<String, Any?> {
        return formField.fieldName to formData
    }
}