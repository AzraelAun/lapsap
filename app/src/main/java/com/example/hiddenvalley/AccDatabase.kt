package com.example.hiddenvalley

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class AccDatabase (context: Context): SQLiteOpenHelper(context, dbname, factory, version){

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table nUser(id integer primary key autoincrement," +
                "username varchar(15),pass varchar(15), email varchar(20),address varchar(50))")
        p0?.execSQL("create table info(id integer primary key autoincrement,"+
                "username varchar(15), pass varchar(15),email varchar(20),address varchar(50),points integer)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS"+ "nUser")
        p0?.execSQL("DROP TABLE IF EXISTS"+ "info")
        onCreate(p0)
    }

    fun insertUserData(username: String , pass: String , email: String , address: String){
        val db: SQLiteDatabase =  writableDatabase
        val values: ContentValues = ContentValues()
        val info: ContentValues = ContentValues()

            values.put("username", username)
            values.put("pass", pass)
            values.put("email", email)
            values.put("address", address)

            info.put("username", username)
            info.put("pass", pass)
            info.put("email", email)
            info.put("address", address)
            info.put("points", 0)

            db.insert("nUser", null, values)
            db.insert("info", null, info)
            db.close()
    }

    fun verifyUsername(username: String): Boolean {
        val db=  writableDatabase
        val query= "select * from user where username = '$username'"
        val cursor= db.rawQuery(query,null)
        if ( cursor.count <= 0){
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun userLogin(username: String,pass: String): Boolean {
        val db=  writableDatabase
        val query= "select * from user where username = '$username' and pass = '$pass'"
        val cursor= db.rawQuery(query,null)
        if ( cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun retrieveData(username: String) : MutableList<UserAdapter>{
        var list : MutableList<UserAdapter> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from info where username = '$username' "
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var user = UserAdapter()
                user.username = result.getString(0)
                user.password = result.getString(1)
                user.email = result.getString(3)
                user.address = result.getString(4)
                user.points = result.getString(5).toInt()
                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
    companion object{
        internal val dbname = "userDB"
        internal val factory = null
        internal val version = 1
    }



}