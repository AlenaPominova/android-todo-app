package com.example.todoapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), IDbHelper {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + TABLE + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK_TITLE + " TEXT NOT NULL, " +
                COL_LAST_UPDATE + " DATETIME NOT NULL);"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE)
        onCreate(db)
    }

    override fun add(todo: ToDo) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_TASK_TITLE, todo.task)
        values.put(COL_LAST_UPDATE, LocalDateTime.now().toString())
        db.insertWithOnConflict(
            TABLE,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
        db.close()
    }

    override fun update(todo: ToDo) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_TASK_TITLE, todo.task)
        values.put(COL_LAST_UPDATE, LocalDateTime.now().toString())
        db.update(TABLE, values, ID + "=?", arrayOf(todo.id.toString())).toLong()
        db.close()
    }

    override fun delete(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE,
            ID + " = ?",
            arrayOf(id.toString())
        )
        db.close()
    }

    override fun loadPage(offset: Int, limit: Int): ArrayList<ToDo> {
        val db = readableDatabase
        val taskList = ArrayList<ToDo>()
        val cursor = db.query(
            TABLE,
            arrayOf(ID, COL_TASK_TITLE),
            null,
            null,
            null,
            null,
            COL_LAST_UPDATE + " DESC",
            "$offset,$limit"
        )
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getColumnIndex(ID)
                val text = cursor.getColumnIndex(COL_TASK_TITLE)
                val todo = ToDo(cursor.getInt(id), cursor.getString(text))
                taskList.add(todo)
            }
        } finally {
            cursor.close()
            db.close()
        }

        return taskList
    }

    companion object {
        const val DB_NAME = "com.pominova.todo.db"
        const val DB_VERSION = 2
        const val TABLE = "tasks"
        const val COL_TASK_TITLE = "title"
        const val COL_LAST_UPDATE = "last_update"
        const val ID = "id"
    }
}