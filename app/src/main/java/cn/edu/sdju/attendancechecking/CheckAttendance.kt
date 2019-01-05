package cn.edu.sdju.attendancechecking

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_check_attendance.*
import kotlinx.android.synthetic.main.activity_list_all.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.info
import java.text.SimpleDateFormat
import java.util.*

class CheckAttendance : AppCompatActivity(), AnkoLogger {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_attendance)
        val bundle = intent.extras
        val name = bundle.get("login_name")

        punch_card_on_work.setOnClickListener {

            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            date.timeZone = TimeZone.getTimeZone("Asia/Shanghai")

            toast("上班打卡成功!")

            val database = Operate(applicationContext)

            database.use {
                createTable(Attendance.TABLE_NAME, true,
                        Attendance.NAME to TEXT,
                        Attendance.INFO to TEXT,
                        Attendance.DATE to TEXT
                )
                insert(Attendance.TABLE_NAME,
                        Attendance.NAME to name,
                        Attendance.INFO to "上班打卡",
                        Attendance.DATE to  date.format(Date())
                )
            }
        }
        punch_card_off_work.setOnClickListener{

            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            date.timeZone = TimeZone.getTimeZone("Asia/Shanghai")

            toast("下班打卡成功!")

            val database = Operate(applicationContext)

            database.use{
                createTable(Attendance.TABLE_NAME, true,
                    Attendance.NAME to TEXT,
                    Attendance.INFO to TEXT,
                    Attendance.DATE to TEXT
            )
                insert(Attendance.TABLE_NAME,
                        Attendance.NAME to name,
                        Attendance.INFO to "下班打卡",
                        Attendance.DATE to  date.format(Date())
                )
            }
        }

        list_personal.setOnClickListener{
            val database = Operate(applicationContext)
            database.use {
                val a = select(Attendance.TABLE_NAME, Attendance.NAME, Attendance.INFO, Attendance.DATE).
                        whereSimple("${Attendance.NAME} = \"$name\"").
                        exec { parseList(classParser<Attendance>())}
                var text = ""
                for (item in a){
                    text += "姓名：" + item.name + " " + "出勤：" + item.info + " " + "时间：" + item.date + "\n"
                }
                list_record.text = text
            }
        }

    }
}
