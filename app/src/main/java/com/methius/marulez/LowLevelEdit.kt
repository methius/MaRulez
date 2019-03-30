package com.methius.marulez

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.low_level_edit.*
import java.util.ArrayList

class LowLevelEdit : AppCompatActivity() {


    var title_trans : String ?= null
    var highLevelNum_trans :Int ?= null
    var lowLevelNum_trans :Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.low_level_edit)


        title_trans = intent.getStringExtra("title_trans")
        highLevelNum_trans = intent.getIntExtra("highLevelNum_trans", 0)
        lowLevelNum_trans = intent.getIntExtra("lowLevelNum_trans", 0)


        var editLowLevel = findViewById<EditText>(R.id.editText)
        var editDetail = findViewById<EditText>(R.id.editText5)


        var highLevel_idx = intent.getIntExtra("highLevel_idx", 0)

        var helper: DBHelper = DBHelper(this)
        var db: SQLiteDatabase = helper.writableDatabase

        var sqlReadLowLevel =
            "SELECT lowLevel from rulezTable WHERE idx=" + highLevel_idx + ""

        var sqlReadDetail =
            "SELECT detail from rulezTable WHERE idx=" + highLevel_idx + ""

        var c1: Cursor = db.rawQuery(sqlReadLowLevel, null)
        c1?.moveToNext()
        var getLowLevel = c1.getString(0)
        editLowLevel.setText(getLowLevel.toString())

        var c2: Cursor = db.rawQuery(sqlReadDetail,null)
        c2?.moveToNext()
        var getDetail = c2.getString(0)
        editDetail.setText(getDetail.toString())

        db.close()

        button11.setOnClickListener { view ->

            var intent = Intent(this, com.methius.marulez.CheckRulezActivity::class.java)


            intent.putExtra("title_trans", title_trans)
            intent.putExtra("highLevelNum_trans", highLevelNum_trans!!)
            intent.putExtra("lowLevelNum_trans", lowLevelNum_trans!!)
            startActivity(intent)
        }


        button12.setOnClickListener { view ->

            var helper: DBHelper = DBHelper(this)
            var db = helper.writableDatabase


            var lowLevelUpdate = editLowLevel?.text.toString()

            var detailUpdate = editDetail?.text.toString()

            var sqlUpdate1 =
                "UPDATE rulezTable SET lowLevel='" + lowLevelUpdate + "' WHERE idx=" + highLevel_idx + ""

            var sqlUpdate2 =
                "UPDATE rulezTable SET detail='" + detailUpdate + "' WHERE idx=" + highLevel_idx + ""

            db.execSQL(sqlUpdate1)
            db.execSQL(sqlUpdate2)
            db.close()

            var intent = Intent(this, com.methius.marulez.CheckRulezActivity::class.java)


            intent.putExtra("title_trans", title_trans)
            intent.putExtra("highLevelNum_trans", highLevelNum_trans!!)
            intent.putExtra("lowLevelNum_trans", lowLevelNum_trans!!)
            startActivity(intent)

        }


    }
}
