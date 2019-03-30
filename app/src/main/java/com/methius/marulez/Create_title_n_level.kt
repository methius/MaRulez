package com.methius.marulez

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.create_title_n_level.*

class Create_title_n_level : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_title_n_level)

        var title: String ?= null
        var title_trans : String ?= null
        var highLevelNum: Int ?= null
        var highLevelNum_trans : Int ?= null
        var lowLevelNum: Int ?= null
        var lowLevelNum_trans : Int ?= null
        var highLevel: String ?= null
        var lowLevel: String ?= null
        var detail: String ?= null



        button10.setOnClickListener { view ->


            var helper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "insert into rulezTable (title, highLevelNum, lowLevelNum, highLevel, lowLevel, detail) values (?, ?, ?,?,?,?)"

            title = editText2.text.toString()
            highLevelNum = Integer.parseInt(editText3.text.toString())
            lowLevelNum = Integer.parseInt(editText4.text.toString())
            highLevel = ""
            lowLevel = ""
            detail = ""


            var arg1 = arrayOf(title, highLevelNum, lowLevelNum,highLevel, lowLevel, detail)

            var tableSize:Int = highLevelNum!! * lowLevelNum!!

            var idx = 0
            while (idx < tableSize) {
                db.execSQL(sql, arg1)
                idx ++
            }




            db.close()

            title_trans = title
            highLevelNum_trans = highLevelNum
            lowLevelNum_trans = lowLevelNum



            var intent = Intent(this, com.methius.marulez.CheckRulezActivity::class.java)
            intent.putExtra("title_trans", title_trans)
            intent.putExtra("highLevelNum_trans", highLevelNum!!)
            intent.putExtra("lowLevelNum_trans", lowLevelNum!!)
            startActivity(intent)
        }
    }
}