package com.example.testingone.sqldatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MySQLDBase(context: Context) : SQLiteOpenHelper(context, "FirstKotlins", null, 1) {

val contexts=context
    override fun onCreate(db: SQLiteDatabase?) {

        val createQry="create table Firsst(fname text,lname text,eMail text)"
        db!!.execSQL(createQry)
        Toast.makeText(contexts,"table created",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}