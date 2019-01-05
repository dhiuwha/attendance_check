package cn.edu.sdju.attendancechecking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        to_register_button.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        login_button.setOnClickListener {
            val database:Operate = Operate(applicationContext)
            database.use {
                createTable(User.TABLE_NAME, true,
                        User.NAME to TEXT,
                        User.PASSWORD to TEXT,
                        User.IDENTITY to TEXT
                )
                val pwd = select(User.TABLE_NAME, User.PASSWORD).
                        whereSimple("${User.NAME} = \"${login_name.text.toString()}\"").
                        exec { parseList(classParser<String>()) }
                if (pwd.isEmpty()){
                    startActivity<RegisterActivity>()
                }else if (pwd[0] == login_pwd.text.toString()){
                    val identity = select(User.TABLE_NAME, User.IDENTITY).whereSimple("${User.NAME} = \"${login_name.text}\"").exec { parseList(classParser<String>()) }[0]
                    if (identity.equals("common")) {
                        startActivity<CheckAttendance>("login_name" to login_name.text.toString())
                    }else{
                        startActivity<ListAll>()
                    }
                }else{
                    toast("密码不正确！")
                    login_pwd.text.clear()
                }
            }
        }

    }
}
