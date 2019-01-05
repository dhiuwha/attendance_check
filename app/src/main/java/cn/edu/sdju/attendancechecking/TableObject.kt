package cn.edu.sdju.attendancechecking

import java.util.*

/**
 * Created by Administrator on 2018/12/31.
 */

data class User(val name:String, val password:String, val identity:String) {
    companion object {
        val TABLE_NAME = "user_profile"
        val NAME = "name"
        val PASSWORD = "password"
        val IDENTITY = "identity"
    }
}
data class Attendance(val name:String, val info:String, val date: String) {
    companion object {
        val TABLE_NAME = "attendance"
        val NAME = "name"
        val INFO = "info"
        val DATE = "date"
    }
}

