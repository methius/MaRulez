package com.methius.marulez

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_check_rulez.*
import java.util.ArrayList
import android.widget.EditText
import android.R.string.cancel
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.Toast




class CheckRulezActivity : AppCompatActivity() {


    var title_array = ArrayList<String>()
    var title_trans : String ?= null

    var highLevel_array = ArrayList<String>()
    var highLevelNum_array = ArrayList<Int>()
    var highLevelNum_trans :Int ?= null
    var highLevel_idx0 : Int ?= null
    var highLevel_idx : Int ?= null

    var lowLevel_array = ArrayList<String>()
    var lowLevelNum_array = ArrayList<Int>()
    var lowLevelNum_trans :Int ?= null


    var idx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_rulez)

        title_trans = intent.getStringExtra("title_trans")
        highLevelNum_trans = intent.getIntExtra("highLevelNum_trans", 0)
        lowLevelNum_trans = intent.getIntExtra("lowLevelNum_trans", 0)

        var helper: DBHelper = DBHelper(this)
        var db: SQLiteDatabase = helper.writableDatabase

        var sql = "select * from rulezTable WHERE title = (?)"
        var arg1 = arrayOf(title_trans)

        var c: Cursor = db.rawQuery(sql, arg1)


        while (c.moveToNext()) {
            var title_pos = c.getColumnIndex("title")
            var highLevelNum_pos = c.getColumnIndex("highLevelNum")
            var lowLevelNum_pos = c.getColumnIndex("lowLevelNum")
            var highLevel_pos = c.getColumnIndex("highLevel")
            var lowLevel_pos = c.getColumnIndex("lowLevel")

            var title = c.getString(title_pos)
            var highLevelNum = c.getInt(highLevelNum_pos)
            var lowLevelNum = c.getInt(lowLevelNum_pos)
            var highLevel = c.getString(highLevel_pos)
            var lowLevel = c.getString(lowLevel_pos)

            title_array.add(title)
            highLevelNum_array.add(highLevelNum)
            lowLevelNum_array.add(lowLevelNum)
            highLevel_array.add(highLevel)
            lowLevel_array.add(lowLevel)

        }

        var sql_title_select = "SELECT idx FROM rulezTable WHERE title='" + title_trans + "'"
        db.rawQuery(sql_title_select, null)
        c.moveToFirst()
        var idx_pos = c.getColumnIndex("idx")
        highLevel_idx0 = c.getInt(idx_pos)


        db.close()

        var adapter = ListAdapter()
        listView2.adapter = adapter

        button7?.setOnClickListener { view ->

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button8?.setOnClickListener { view ->

            var helper: DBHelper = DBHelper(this)
            var db: SQLiteDatabase = helper.writableDatabase

            var highLevelUpdatePre: String? = ""
            var idx = 0
            while (idx < highLevelNum_trans!! * lowLevelNum_trans!!) {

                var view = listView2.getChildAt(idx)
                var text1 = view.findViewById<EditText>(R.id.editText1)
                var highLevelUpdate = text1.getText().toString()
//                if (highLevelUpdate == "") {
//                    highLevelUpdate = highLevelUpdatePre.toString()
//                }
                for ( i in 0 until lowLevelNum_trans!!) {
                    highLevel_idx = highLevel_idx0!! + idx
                    var sqlUpdate =
                        "UPDATE rulezTable SET highLevel='" + highLevelUpdate + "' WHERE idx=" + highLevel_idx + ""
                    db.execSQL(sqlUpdate)
                    highLevelUpdatePre = highLevelUpdate
                    idx++
                }
            }
            db.close()
        }

        button9?.setOnClickListener { view ->

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Are you sure you want to this rules?")
            // OK 버튼 이벤트
            dialog.setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, which ->

                var helper: DBHelper = DBHelper(this)
                var db: SQLiteDatabase = helper.writableDatabase
                var sqlDelete = "DELETE FROM rulezTable WHERE title = '" + title_trans + "'"
                db.execSQL(sqlDelete)

                db.close()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            })
            // Cancel 버튼 이벤트
            dialog.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            dialog.show()



//            var helper: DBHelper = DBHelper(this)
//            var db: SQLiteDatabase = helper.writableDatabase
//            var sqlDelete = "DELETE FROM rulezTable WHERE title = '" + title_trans + "'"
//            db.execSQL(sqlDelete)
//
//            db.close()
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }

    inner class ListAdapter : BaseAdapter() {

        var listener = BtnListener()

        override fun getCount(): Int {
            return lowLevel_array.size
        }

        override fun getItem(p0: Int): Any? {
            return null
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
            var convertView: View? = p1
            var title_trans = intent.getStringExtra("title_trans")
            var lowLevelNum_trans = intent.getIntExtra("lowLevelNum_trans", 0)

            if (p1 == null) {
                convertView = layoutInflater.inflate(R.layout.overview, null)
            }

            var text1: EditText? = convertView?.findViewById<EditText>(R.id.editText1)
            var button2: Button? = convertView?.findViewById<Button>(R.id.button2)
            var button3: Button? = convertView?.findViewById<Button>(R.id.button3)
            var button4: Button? = convertView?.findViewById<Button>(R.id.button4)
            var button5: Button? = convertView?.findViewById<Button>(R.id.button5)
            var button6: Button? = convertView?.findViewById<Button>(R.id.button6)
            var overview: LinearLayout? = convertView?.findViewById<LinearLayout>(R.id.overview)
            var buttons: LinearLayout? = convertView?.findViewById<LinearLayout>(R.id.buttons)
            var buttonList = arrayOf(button2, button3, button4, button5, button6)

            button2?.tag = p0
            button3?.tag = p0
            button4?.tag = p0
            button5?.tag = p0
            button6?.tag = p0


            if (highLevel_array[p0] == "" && lowLevel_array[p0] == "" && (p0) % lowLevelNum_trans == 0) {
                text1?.setHint("Fill in the category name")
//                ButtonList(buttonList,lowLevelNum_trans,lowLevel_array,p0)

                if (lowLevelNum_trans == 1) {
                    button2?.text = ""
                    button3?.setVisibility(View.GONE)
                    button4?.setVisibility(View.GONE)
                    button5?.setVisibility(View.GONE)
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 2) {
                    button2?.text = ""
                    button3?.text = ""
                    button4?.setVisibility(View.GONE)
                    button5?.setVisibility(View.GONE)
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 3) {
                    button2?.text = ""
                    button3?.text = ""
                    button4?.text = ""
                    button5?.setVisibility(View.GONE)
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 4) {
                    button2?.text = ""
                    button3?.text = ""
                    button4?.text = ""
                    button5?.text = ""
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 5) {
                    button2?.text = ""
                    button3?.text = ""
                    button4?.text = ""
                    button5?.text = ""
                    button6?.text = ""
                }

            } else if (highLevel_array.size > 0 && (p0) % lowLevelNum_trans == 0) {
                text1?.setText(highLevel_array[p0])
                if (lowLevelNum_trans == 1) {
                    button2?.text = lowLevel_array[p0]
                    button3?.setVisibility(View.GONE)
                    button4?.setVisibility(View.GONE)
                    button5?.setVisibility(View.GONE)
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 2) {
                    button2?.text = lowLevel_array[p0]
                    button3?.text = lowLevel_array[p0 + 1]
                    button4?.setVisibility(View.GONE)
                    button5?.setVisibility(View.GONE)
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 3) {
                    button2?.text = lowLevel_array[p0]
                    button3?.text = lowLevel_array[p0 + 1]
                    button4?.text = lowLevel_array[p0 + 2]
                    button5?.setVisibility(View.GONE)
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 4) {
                    button2?.text = lowLevel_array[p0]
                    button3?.text = lowLevel_array[p0 + 1]
                    button4?.text = lowLevel_array[p0 + 2]
                    button5?.text = lowLevel_array[p0 + 3]
                    button6?.setVisibility(View.GONE)
                } else if (lowLevelNum_trans == 5) {
                    button2?.text = lowLevel_array[p0]
                    button3?.text = lowLevel_array[p0 + 1]
                    button4?.text = lowLevel_array[p0 + 2]
                    button5?.text = lowLevel_array[p0 + 3]
                    button6?.text = lowLevel_array[p0 + 4]
                }
            } else {
                buttons?.setVisibility(View.GONE)
                text1?.setVisibility(View.GONE)
            }

            button2?.setOnClickListener(listener)
            button3?.setOnClickListener(listener)
            button4?.setOnClickListener(listener)
            button5?.setOnClickListener(listener)
            button6?.setOnClickListener(listener)

            return convertView
        }

    }


    inner class BtnListener : View.OnClickListener {
        override fun onClick(p0: View?) {

            idx = p0?.tag as Int

            when(p0?.id){
                R.id.button2 ->
                    highLevel_idx = highLevel_idx0!! + idx + 0
                R.id.button3 ->
                    highLevel_idx = highLevel_idx0!! + idx + 1
                R.id.button4 ->
                    highLevel_idx = highLevel_idx0!! + idx + 2
                R.id.button5 ->
                    highLevel_idx = highLevel_idx0!! + idx + 3
                R.id.button6 ->
                    highLevel_idx = highLevel_idx0!! + idx + 4

            }



            var intent = Intent(this@CheckRulezActivity, LowLevelEdit::class.java)

            intent.putExtra("title_trans", title_trans)
            intent.putExtra("highLevelNum_trans", highLevelNum_trans)
            intent.putExtra("lowLevelNum_trans", lowLevelNum_trans)
            intent.putExtra("highLevel_idx", highLevel_idx)
            startActivity(intent)

        }


//    fun ButtonList (bList :Array<Button?>, showNum:Int, text_array:ArrayList<String>, viewNum:Int)  {
//
//        var bIdx = 0
//        var bList=bList
//        var bShowNum = showNum
//        var bTextArray = text_array
//        var viewIdx = viewNum
//        while (bIdx < bShowNum ) {
//            if (bTextArray[viewIdx] == null ) {
//                bList[bIdx]?.text = "TYPE IN_low"
//            }
//            else if (bTextArray[viewIdx] != null ) {
//                bList[bIdx]?.text = bTextArray[viewIdx]
//            }
//            bIdx++
//            viewIdx++
//        }
//        while (bIdx < 6 ) {
//            bList[bIdx]?.setVisibility(View.GONE)
//            bIdx++
//        }
//  }


    }
}




