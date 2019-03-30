package com.methius.marulez

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val CheckRulezActivity = 1
    val Create_title_n_level = 2
    var title_array = ArrayList<String>()
    var title_trans : String ?= null
    var highLevelNum_array = ArrayList<Int>()
    var highLevelNum_trans: Int ?= null
    var lowLevelNum_array = ArrayList<Int>()
    var lowLevelNum_trans: Int ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = "Check Your Rulez!"

        var helper: DBHelper = DBHelper(this)
        var db: SQLiteDatabase = helper.writableDatabase

        var sql = "select * from rulezTable"

        var c: Cursor = db.rawQuery(sql, null)


        var title_before :String ?=null
        while (c.moveToNext()) {
            var title_pos = c.getColumnIndex("title")
            var highLevelNum_pos = c.getColumnIndex("highLevelNum")
            var lowLevelNum_pos = c.getColumnIndex("lowLevelNum")

            var title = c.getString(title_pos)
            var highLevelNum = c.getInt(highLevelNum_pos)
            var lowLevelNum = c.getInt(lowLevelNum_pos)

            if (title == title_before){
                continue
            }
            title_array.add(title)
            highLevelNum_array.add(highLevelNum)
            lowLevelNum_array.add(lowLevelNum)
            title_before = title
        }

        db.close()

        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, title_array)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, i, l ->

            title_trans = title_array[i]
            highLevelNum_trans = highLevelNum_array[i]
            lowLevelNum_trans = lowLevelNum_array[i]

            var intent = Intent(this, com.methius.marulez.CheckRulezActivity::class.java)
            intent.putExtra("title_trans", title_trans)
            intent.putExtra("highLevelNum_trans", highLevelNum_trans!!)
            intent.putExtra("lowLevelNum_trans", lowLevelNum_trans!!)
            startActivity(intent)

        }


        button.setOnClickListener { view ->
            var intent = Intent(this, com.methius.marulez.Create_title_n_level::class.java)
            startActivity(intent)
        }


        }

    }
