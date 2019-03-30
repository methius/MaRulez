package com.methius.marulez

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DBHelper(context: Context) : SQLiteOpenHelper(context, "Rulez.db", null, 1){




    override fun onCreate(p0: SQLiteDatabase?) {
        Log.d("msg", "on create")

        var sql = "create table rulezTable (" +
                "idx integer primary key autoincrement, " +
                "title text, " +
                "highLevelNum integer," +
                "lowLevelNum integer," +
                "highLevel text, " +
                "lowLevel text, " +
                "detail text" +
                ")"

        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("msg", "oldVersion : ${oldVersion}, newVersion : ${newVersion}")
    }

//    fun update_highLevel(title : String? , highLevelUpdate : String?) {
//        var db: SQLiteDatabase = writableDatabase
//
//        db?.execSQL("UPDATE rulezTable SET highLevel=" + highLevelUpdate + " WHERE title='" + title + "';")
//        db?.close()
//    }



}


