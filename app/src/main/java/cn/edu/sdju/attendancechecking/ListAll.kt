package cn.edu.sdju.attendancechecking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_list_all.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class ListAll : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_all)
        list_button.setOnClickListener {
            val database = Operate(applicationContext)
            database.use {
                val a = select(Attendance.TABLE_NAME, Attendance.NAME, Attendance.INFO, Attendance.DATE).exec { parseList(classParser<Attendance>())}
                var text = ""
                for (item in a){
                    text += "姓名：" + item.name + " " + "出勤：" + item.info + " " + "时间：" + item.date + "\n"
                }
                list_all.text = text
            }

        }
        }

}
