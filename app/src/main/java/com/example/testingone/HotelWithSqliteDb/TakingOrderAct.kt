package com.example.testingone.HotelWithSqliteDb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingone.R
import com.example.testingone.adapters.SQliteAdapter
import com.example.testingone.sqldatabase.MySQLDBase
import kotlinx.android.synthetic.main.activity_first_actity.*
import kotlinx.android.synthetic.main.activity_taking_order.*
import java.util.LinkedHashMap
import kotlin.properties.Delegates

class TakingOrderAct : AppCompatActivity() {

    lateinit var list:ArrayList<String>
    lateinit var dlist:ArrayList<String>
    lateinit var hotelData:ArrayList<HotelModel>

     var qty:Int = 0
    lateinit var existing_customer_HashMap: LinkedHashMap<String, String>
    lateinit var existing_customerList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taking_order)
        initializeDropdown()
    }
    fun initializeDropdown(){

        hotelData= ArrayList()
        list=ArrayList()
        list.add("-- Qty --")
        list.add("1")
        list.add(" 2 ")
        list.add(" 3 ")
        list.add(" 4 ")
        list.add(" 5 ")
        list.add(" 6 ")

        val qtyAdapter=ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,list)
        item_qnty.adapter=qtyAdapter
        item_qnty.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position!=0) {
                    qty = parent!!.getItemAtPosition(position).toString().trim().toInt()
                }
            }
        }
        
        val dbAccess= MySQLDBase(this)
        val readAccess=dbAccess.readableDatabase
        val cursor=readAccess.rawQuery("select * from ItemsInsertion",null)

        //adding item names to the spinner
        existing_customer_HashMap=LinkedHashMap<String, String>()
        existing_customer_HashMap.put("-- please select Item--","-- please select Item--")
        dlist= ArrayList()
        dlist.add("-- Select Item Name --")
        if (cursor.moveToFirst())
        {
            do {
                existing_customer_HashMap.put(""+cursor.getString(0),""+cursor.getString(3))
                existing_customerList = ArrayList(existing_customer_HashMap.keys)
            }while (cursor.moveToNext())

            val qtyAdapter1=ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,existing_customerList)
            item_names.adapter=qtyAdapter1
            cursor.close()
        }

        item_names.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position!=0) {
                    val tffinqty =
                        existing_customer_HashMap.values.elementAt(position).toDouble() * qty
                    Toast.makeText(this@TakingOrderAct, "" + tffinqty, Toast.LENGTH_SHORT).show()
                    hotelData.add(HotelModel(existing_customer_HashMap.keys.elementAt(position), "" + tffinqty, "" + qty, existing_customer_HashMap.values.elementAt(position)))
                }
            }
        }

    }


}
