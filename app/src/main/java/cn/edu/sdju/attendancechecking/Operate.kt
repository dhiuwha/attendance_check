package cn.edu.sdju.attendancechecking

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.*

/**
 * Created by Administrator on 2018/12/31.
 */
class Operate(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {

    companion object {
        private var instance: Operate? = null

        @Synchronized
        fun getInstance(ctx: Context): Operate {
            if (instance == null) {
                instance = Operate(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(User.TABLE_NAME, true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        print(111111)

        db.createTable(User.TABLE_NAME, true,
                User.NAME to TEXT,
                User.PASSWORD to TEXT,
                User.IDENTITY to TEXT
        )


    }

    val Context.database: Operate
        get() = Operate.getInstance(getApplicationContext())

}
