package com.example.testingone.sqldatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingone.R
import com.example.testingone.Util.UImportant
import com.example.testingone.adapters.SQliteAdapter
import com.example.testingone.model.SqlModels
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.ading_data_to_databse.*
import kotlinx.android.synthetic.main.ading_data_to_databse.view.*
import java.util.regex.Pattern
import java.util.zip.Inflater

class FirstAct : AppCompatActivity() {

    lateinit var adapter:SQliteAdapter
    lateinit var layouts:View
    lateinit var mAlert:AlertDialog
    var emailVailds: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    lateinit var dlist:ArrayList<SqlModels>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        dlist=ArrayList()

//        save_btn.setOnClickListener {
//
//            //save_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.btn_fade_in))
//            SaveDataToDataBAse()
//        }

        adding_data.setOnClickListener {

            if (dlist.size > 0) {
                dlist.clear()

                adapter.notifyDataSetChanged()
            }
                layouts = LayoutInflater.from(this).inflate(R.layout.ading_data_to_databse, null)
                val dialog = AlertDialog.Builder(this)
                dialog.setView(layouts)
                dialog.setCancelable(false)
                mAlert = dialog.show()

                layouts.save_btn.setOnClickListener {
                    SaveDataToDataBAse()
                    mAlert.dismiss()
                }

        }

    }
    fun SaveDataToDataBAse(){

        if (UImportant.emptyCheckEditBox(layouts.person_name_input,"Should not be Empty")){
            if (UImportant.emptyCheckEditBox(layouts.person_lname_input,"Should not be Empty")){
                if (UImportant.emptyCheckEditBox(layouts.person_email_input,"Should not be Empty")){
                    if (Pattern.compile(emailVailds).matcher(layouts.person_email_input.text.toString()).matches()){

                        //here write code for insert data intoi database
                        codeForSQLiteDBInsertion(layouts.person_name_input.text.toString(),layouts.person_lname_input.text.toString().trim(),layouts.person_email_input.text.toString().trim())
                    }else{
                        Toast.makeText(this,"Enter a valid Email",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dbAccess=MySQLDBase(this)
        val readAccess=dbAccess.readableDatabase
        val cursor=readAccess.rawQuery("select * from Firsst",null)

        if (cursor.moveToFirst())
        {
            val ss=cursor.getString(2)
                do {
                    dlist.add(SqlModels(cursor.getString(0),cursor.getString(1),cursor.getString(2)))

                }while (cursor.moveToNext())
            for (i in 0..dlist.size-1)
            {
                if (dlist.get(i).eMails.equals(ss)){

                }else{
                    adapter=SQliteAdapter(this,dlist)
                    recycler_view_list.layoutManager= LinearLayoutManager(this)
                    recycler_view_list.adapter=adapter
                }
            }
            cursor.close()
        }

    }

    fun codeForSQLiteDBInsertion(fName:String,lName:String,Email:String){

        val dbAccess=MySQLDBase(this)
        val ss=dbAccess.writableDatabase

        val insertQry="insert into Firsst values('"+fName+"','"+lName+"','"+Email+"')"

        ss.execSQL(insertQry)

        //val dbAcces=MySQLDBase(this)
        val readAccess=dbAccess.readableDatabase
        val cursor=readAccess.rawQuery("select * from Firsst",null)

        if (cursor.moveToFirst())
        {
            val ss=cursor.getString(2)
            do {
                dlist.add(SqlModels(cursor.getString(0),cursor.getString(1),cursor.getString(2)))

            }while (cursor.moveToNext())
            for (i in 0..dlist.size-1)
            {
                if (dlist.get(i).eMails.equals(ss)){

                }else{
                    adapter=SQliteAdapter(this,dlist)
                    recycler_view_list.layoutManager= LinearLayoutManager(this)
                    recycler_view_list.adapter=adapter
                }
            }
            cursor.close()
        }

//       Handler().postDelayed(Runnable { object :Runnable{
//           override fun run() {

               //code to read data from the database
//               val readAccess=dbAccess.readableDatabase
//               val cursor=readAccess.rawQuery("select * from Firsst",null)
//
//               if (cursor.moveToFirst())
//               {
//                   do {
//                       dlist.add(SqlModels(cursor.getString(0),cursor.getString(1),cursor.getString(2)))
//
//                   }while (cursor.moveToNext());
//                   cursor.close()
//               }

//           }
//       } },2000)

    }
}
