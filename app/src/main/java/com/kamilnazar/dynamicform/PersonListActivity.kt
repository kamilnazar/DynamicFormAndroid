package com.kamilnazar.dynamicform

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamilnazar.dynamicform.dynamicform.DynamicFormActivity
import com.kamilnazar.dynamicform.dynamicform.RecordListAdapter
import kotlinx.android.synthetic.main.activity_persons.*

class PersonListActivity : AppCompatActivity() {
    private val TAG = PersonListActivity::class.java.simpleName
    private val adpter = RecordListAdapter(mutableListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)

        add_person.setOnClickListener { DynamicFormActivity.start(this, DYNAMIC_FORM_REQUEST) }
        record_list.layoutManager = LinearLayoutManager(this)
        record_list.adapter = adpter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == DYNAMIC_FORM_REQUEST) {
            data?.let {
                val formData =
                    it.getSerializableExtra(DynamicFormActivity.DYNAMIC_FORM_DATA) as HashMap<String, Any?>
                adpter.addRecord(formData)
                setTotalCount()
                Log.d(TAG, formData.toString())

            }
        }
    }

    private fun setTotalCount(){
        total_reports.text = adpter.itemCount.toString()
    }

    companion object {
        private const val DYNAMIC_FORM_REQUEST = 548
    }
}