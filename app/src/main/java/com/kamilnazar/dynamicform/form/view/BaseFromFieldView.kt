package com.kamilnazar.dynamicform.form.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.kamilnazar.dynamicform.form.serializer.FormField
import com.kamilnazar.dynamicform.util.dpToPx

abstract class BaseFromFieldView<T, V : View>(
    private val formField: FormField,
    private val context: Context
) : BaseView() {
    override val view: View
        get() = container
    val inputWidget: V by lazy(LazyThreadSafetyMode.NONE) { generateInputWidget() }
    private val container by lazy(LazyThreadSafetyMode.NONE) { generateContainer() }

    abstract fun getCleanedData(): T?
    protected abstract fun generateInputWidget(): V

    private fun generateContainer(): LinearLayout {
        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(PADDING_DP.dpToPx())
        }
        val label = generateLabel()
        container.addView(label)
        container.addView(inputWidget)
        return container
    }

    private fun generateLabel(): TextView {
        return TextView(context).apply {
            text = formField.fieldName.capitalize()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    companion object {
        private const val PADDING_DP = 8
    }
}