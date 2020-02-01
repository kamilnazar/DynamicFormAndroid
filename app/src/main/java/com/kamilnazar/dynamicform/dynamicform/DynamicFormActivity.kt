package com.kamilnazar.dynamicform.dynamicform

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kamilnazar.dynamicform.R
import com.kamilnazar.dynamicform.form.serializer.Form
import com.kamilnazar.dynamicform.form.view.FormView
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_dynamic_form.*
import org.json.JSONObject
import java.nio.charset.Charset


class DynamicFormActivity : AppCompatActivity() {
    private val TAG = DynamicFormActivity::class.java.simpleName
    lateinit var formView: FormView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_form)
        deserializeJson()?.let {
            generateView(root, it)
        }
        record_add.setOnClickListener { getDataFromForm() }
    }

    private fun deserializeJson(): Form? {
        val moshi = Moshi.Builder().build()
        val formAdapter = moshi.adapter<Form>(
            Form::class.java
        )
        val form = formAdapter.fromJson(readAssetFile())
        if (form == null)
            Toast.makeText(this, "Reading asset file failed", Toast.LENGTH_SHORT).show()
        return form
    }

    private fun readAssetFile(): String {
        val iStream = assets.open(DYNAMIC_FORM_NAME)
        val buffer = ByteArray(iStream.available())
        iStream.read(buffer)
        return String(buffer, Charset.forName("UTF-8"))
    }

    private fun generateView(rootView: LinearLayout, form: Form) {
        formView = FormView(this, form)
        rootView.addView(formView.generateView())
    }

    private fun getDataFromForm() {
        if (formView.validateAll()) {
            if (formView.checkAtleastOneFieldHasData()) {
                val formData = formView.getFormData()
                val json = JSONObject(formData).toString()
                Log.d(TAG, json)
                Toast.makeText(this, json, Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(DYNAMIC_FORM_DATA, formData)
                })
                finish()
            } else Toast.makeText(this, "One field is mandatory", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Form contains errors", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val DYNAMIC_FORM_NAME = "dynamicform.json"
        const val DYNAMIC_FORM_DATA = "dynamic_form_data"
        @JvmStatic
        fun start(context: Activity, request: Int) {
            context.startActivityForResult(
                Intent(context, DynamicFormActivity::class.java),
                request
            )
        }
    }
}