package com.example.testingone.sqldatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.testingone.R
import com.example.testingone.Util.UImportant
import kotlinx.android.synthetic.main.activity_first.*
import java.util.regex.Pattern

class FirstAct : AppCompatActivity() {

    var emailVailds: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)


        save_btn.setOnClickListener {

            //save_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.btn_fade_in))
            SaveDataToDataBAse()
        }
    }
    fun SaveDataToDataBAse(){

        if (UImportant.emptyCheckEditBox(person_name_input,"Should not be Empty")){
            if (UImportant.emptyCheckEditBox(person_lname_input,"Should not be Empty")){
                if (UImportant.emptyCheckEditBox(person_email_input,"Should not be Empty")){

                    if (Pattern.compile(emailVailds).matcher(person_email_input.text.toString()).matches()){

                        //here write code for insert data intoi database
                        codeForSQLiteDBInsertion(person_name_input.text.toString(),person_lname_input.text.toString().trim(),person_email_input.text.toString().trim())
                    }else{
                        Toast.makeText(this,"Enter a valid Email",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun codeForSQLiteDBInsertion(fName:String,lName:String,Email:String){

        val dbAccess=MySQLDBase(this)
        val ss=dbAccess.writableDatabase

        val insertQry="insert into Firsst values('"+fName+"','"+lName+"','"+Email+"')"

        ss.execSQL(insertQry)


//       Handler().postDelayed(Runnable { object :Runnable{
//           override fun run() {

               //code to read data from the database
               val readAccess=dbAccess.readableDatabase
               val cursor=readAccess.rawQuery("select * from Firsst",null)

               if (cursor.moveToFirst())
               {
                   do {
                       Toast.makeText(this@FirstAct,""+cursor.getString(0),Toast.LENGTH_SHORT).show()
                   }while (cursor.moveToNext());
                   cursor.close()
               }

//           }
//       } },2000)

    }
}
