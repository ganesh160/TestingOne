package com.example.testingone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.testingone.Util.UImportant.Companion.hideKeyboard
import com.example.testingone.workingwithAdunits.MyWacther
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_sample_testings.*

class SampleTestings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_testings)


        edit_text.addTextChangedListener(texts)
        button.setOnClickListener {
            if (performData()){
                Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
            }
        }
    }

    val texts= object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

                if (performData()) {
                    Toast.makeText(this@SampleTestings, "success", Toast.LENGTH_SHORT).show()
                }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    private fun performData() :Boolean {

        if (checkEmpty(edit_text,"Enter valid name",input_lyt)){
            return true
        }
            return false
    }

    fun checkEmpty(editText1: EditText,errorMsg:String,ssp:TextInputLayout) : Boolean{

        if (editText1.text.toString().trim { it <= ' ' }.length >= 3) {
            ssp.setError(null)
            return true
        } else {
            ssp.setError(errorMsg)
            ssp.requestFocus()
            hideKeyboard(ssp)
            return false
        }
    }


}
