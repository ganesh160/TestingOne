package com.example.testingone.HotelWithSqliteDb

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingone.R
import com.example.testingone.Util.UImportant
import com.example.testingone.adapters.SQliteAdapter
import com.example.testingone.model.SqlModels
import com.example.testingone.sqldatabase.MySQLDBase
import com.example.testingone.sqldatabase.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_first_actity.*
import kotlinx.android.synthetic.main.adding_hotel_item_info.view.*
import kotlinx.android.synthetic.main.ading_data_to_databse.view.*
import java.util.zip.Inflater

class FirstActity : AppCompatActivity(),ForDeleting {

    lateinit var layout:View
    lateinit var mAlert:AlertDialog
    lateinit var dlist:ArrayList<HotelModel>
    lateinit var shared:SharedPreferences
    lateinit var spEditor:SharedPreferences.Editor
    lateinit var adapter:SQliteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_actity)

        shared =getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE)
        val b=shared.getString("executed", "")
        if (b.equals("true",ignoreCase = true)){
            displayStoredData()
        }

        listeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.adding_menus ->{
                //here code for adding data into the pertivular restaurant
                layout = LayoutInflater.from(this).inflate(R.layout.adding_hotel_item_info, null)
                val dialog = AlertDialog.Builder(this)
                dialog.setView(layout)
                dialog.setCancelable(false)
                mAlert = dialog.show()
                layout.save_items_btn.setOnClickListener {
                    perfromInsertionLogic()
                }
                true
            }
            R.id.adding_order_menu ->{
                //here code for adding data into the pertivular restaurant

                val orderAct=Intent(this,TakingOrderAct::class.java)
                startActivity(orderAct)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun perfromInsertionLogic(){
        if (UImportant.emptyCheckEditBoxHotel(layout.item_name_input,"Should not be Empty")){
            if (UImportant.emptyCheckEditBoxHotel(layout.item_price_input,"Should not be Empty")){
                if (UImportant.emptyCheckEditBoxHotel(layout.item_gst_input,"Should not be Empty")){

                    var gst=0.01*layout.item_gst_input.text.toString().toInt()
                    var itemPrice=gst*layout.item_price_input.text.toString().toInt()
                    var total=itemPrice+layout.item_price_input.text.toString().toInt()

                    val dbAccess=MySQLDBase(this)
                    val ss=dbAccess.writableDatabase

                    val insertQry="insert into ItemsInsertion values('"+layout.item_name_input.text.toString()+"','"+layout.item_price_input.text.toString()+"','"+gst+"','"+total+"')"

                    ss.execSQL(insertQry)

                    mAlert.dismiss()

                    shared = getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE)
                    spEditor=shared.edit()
                    spEditor.putString("executed", "true")
                    spEditor.apply()
                    spEditor.commit()

                    displayStoredData()
                }
            }
        }
    }

    fun displayStoredData(){

        val dbAccess= MySQLDBase(this)
        val readAccess=dbAccess.readableDatabase
        val cursor=readAccess.rawQuery("select * from ItemsInsertion",null)

        dlist= ArrayList()
        if (cursor.moveToFirst())
        {
            val ss=cursor.getString(2)
            do {
                 dlist.add(HotelModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)))
            }while (cursor.moveToNext())
            adapter= SQliteAdapter(this,dlist)
            hotel_data_recyclerview.layoutManager= LinearLayoutManager(this)
            hotel_data_recyclerview.adapter=adapter
            adapter.callBack(this)
            cursor.close()

        }
    }

    override fun deleteImg(file: HotelModel) {

        val alert=AlertDialog.Builder(this)
        alert.setTitle("Do You want to update ")
        alert.setCancelable(false)
        alert.setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
            CodeForUpdateItem(file.iNames,file.icost,file.igsts)
            dialog.dismiss()
        })
        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        alert.setNeutralButton("Delete", DialogInterface.OnClickListener { dialog, which ->
            val dbAccess= MySQLDBase(this)
            val deletExe=dbAccess.readableDatabase
            val delete="delete from ItemsInsertion where iName='"+file.iNames+"'"
            deletExe.execSQL(delete)
            Toast.makeText(this,"Deleted Succesfully",Toast.LENGTH_SHORT).show()
            displayStoredData()
            dialog.dismiss()
        })
        alert.show()
    }

    private fun CodeForUpdateItem(iNames: String, icost: String, igsts: String) {

        layout = LayoutInflater.from(this).inflate(R.layout.adding_hotel_item_info, null)
        val dialog = AlertDialog.Builder(this)
        dialog.setView(layout)
        dialog.setCancelable(false)
        mAlert = dialog.show()

        layout.save_items_btn.visibility=View.GONE
        layout.update_items_btn.visibility=View.VISIBLE

        layout.item_name_input.setText(""+iNames)
        layout.item_price_input.setText(""+icost)
        layout.item_gst_input.setText(""+igsts)

        layout.item_name_input.isFocusableInTouchMode=false
        layout.update_items_btn.setOnClickListener {

            if (UImportant.emptyCheckEditBoxHotel(layout.item_name_input,"Should not be Empty")){
                if (UImportant.emptyCheckEditBoxHotel(layout.item_price_input,"Should not be Empty")){
                    if (UImportant.emptyCheckEditBoxHotel(layout.item_gst_input,"Should not be Empty")){

                        var gst=1*layout.item_gst_input.text.toString().toDouble()
                        var itemPrice=gst*layout.item_price_input.text.toString().toInt()
                        var total=itemPrice+layout.item_price_input.text.toString().toInt()

                        val dbAccess=MySQLDBase(this)
                        val ss=dbAccess.writableDatabase

                        val insertQry="update  ItemsInsertion set iCost='"+layout.item_price_input.text.toString()+"',igst='"+gst+"',itotls='"+total+"' where iName='"+layout.item_name_input.text.toString()+"'"
                       // val insertQry="insert into ItemsInsertion values('"+layout.item_name_input.text.toString()+"','"+layout.item_price_input.text.toString()+"','"+gst+"','"+total+"')"

                        ss.execSQL(insertQry)

                        mAlert.dismiss()

                        displayStoredData()
                    }
                }
            }
        }

    }

    //ths code for filter with recyclerview
    fun listeners()
    {
        edittext_for_search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if (s!!.toString().equals("",ignoreCase = true)||s!!.toString().equals("null",ignoreCase = true)) {
                    adapter=SQliteAdapter(this@FirstActity,dlist)
                    hotel_data_recyclerview.layoutManager= LinearLayoutManager(this@FirstActity)
                    hotel_data_recyclerview.adapter=adapter
                    adapter.callBack(this@FirstActity)

                }else {
                    adapter.filter.filter(s!!.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

}
