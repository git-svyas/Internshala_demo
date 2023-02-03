package com.example.internshala_demo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MyDBHelper(context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,"WORKSHOP",null,1) {


    companion object{
        private val DB_NAME = "task"
        private val DB_VERSION = 1
        private val TABLE_NAME="WORKSHOP"
        private val ID = "id"
        private val NAME = "name"
        private val DETAILS ="applied"

    }


    override fun onCreate(db: SQLiteDatabase?) {


        val CREATE_TABLE = "CREATE TABLE WORKSHOP($ID INTEGER PRIMARY KEY AUTOINCREMENT,$NAME TEXT,$DETAILS APPLIED)"

        db?.execSQL(CREATE_TABLE)

        val values=ContentValues()

        values.put(ID,1)
        values.put(NAME,"Java")
        values.put(DETAILS,"NO")
        db?.insert(TABLE_NAME,null, values)

        values.put(ID,2)
        values.put(NAME,"Python")
        values.put(DETAILS,"NO")
        db?.insert(TABLE_NAME,null, values)

        values.put(ID,3)
        values.put(NAME,"Kotlin")
        values.put(DETAILS,"NO")
        db?.insert(TABLE_NAME,null, values)

        values.put(ID,4)
        values.put(NAME,"Dart")
        values.put(DETAILS,"NO")
        db?.insert(TABLE_NAME,null, values)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getName(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + "WORKSHOP", null)

    }

    fun addTasks(name:String,applied:String){
        val db = writableDatabase
        val values = ContentValues()
        values.put(NAME,name)
        values.put(DETAILS,applied)
        db.insert(TABLE_NAME,null,values)
        Log.d("NAME",name)
        Log.d("DETAILS",applied)
        db.close()

    }

    fun updateTask(data : String,position: Int){
        val db = writableDatabase
        val values=ContentValues()

        var num=position
        num+=1

        values.put(ID,num)
        values.put(NAME,data)
        values.put(DETAILS,"YES")
        db.update(TABLE_NAME, values,"id="+num,null)
        db.close()
    }



}