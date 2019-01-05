package cn.edu.sdju.attendancechecking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Suppress("IMPLICIT_CAST_TO_ANY")
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register_button.setOnClickListener {
            val database = Operate(applicationContext)
            database.use{
                val name = select(User.TABLE_NAME, User.NAME).
                        whereSimple("${User.NAME} = \"${register_name.text}\"").
                                exec { parseList(classParser<String>()) }
                if (name.isEmpty()) {
                    insert(User.TABLE_NAME,
                            User.NAME to register_name.text.toString(),
                            User.PASSWORD to register_pwd.text.toString(),
                            User.IDENTITY to "common"
                    )
                    startActivity<LoginActivity>()
                }else{
                    toast("该用户名已被注册")
                    register_name.text.clear()
                    register_pwd.text.clear()
                }
            }
        }

    }
}
