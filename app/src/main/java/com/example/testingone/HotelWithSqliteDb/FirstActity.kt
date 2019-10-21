package com.example.testingone.HotelWithSqliteDb

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingone.R
import com.example.testingone.Util.UImportant
import com.example.testingone.adapters.SQliteAdapter
import com.example.testingone.model.SqlModels
import com.example.testingone.sqldatabase.MySQLDBase
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_first_actity.*
import kotlinx.android.synthetic.main.adding_hotel_item_info.view.*
import kotlinx.android.synthetic.main.ading_data_to_databse.view.*
import java.util.zip.Inflater

class FirstActity : AppCompatActivity(),ForDeleting {

    override fun deleteImg(file: HotelModel) {

        val alert=AlertDialog.Builder(this)
        alert.setTitle("Do You want to update ")
        alert.setCancelable(false)
        alert.setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this,""+file.itotals,Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        })
        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        alert.show()

    }

    lateinit var layout:View
    lateinit var mAlert:AlertDialog
    lateinit var dlist:ArrayList<HotelModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_actity)
        displayStoredData()
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
                val adapter= SQliteAdapter(this,dlist)
                hotel_data_recyclerview.layoutManager= LinearLayoutManager(this)
                hotel_data_recyclerview.adapter=adapter
                adapter.callBack(this)
            }while (cursor.moveToNext())

            cursor.close()
        }
    }
}
