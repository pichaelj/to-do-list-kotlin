package com.brocodes.todoapparchitecture.sqllitedatabaseutils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.brocodes.todoapparchitecture.model.Task
import com.brocodes.todoapparchitecture.sqllitedatabaseutils.TaskEntryContract.TaskEntry.Companion.TABLE_NAME

class TaskDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addTask(task: Task): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(TaskEntryContract.TaskEntry.COLUMN_NAME_TITLE, task.task)
        values.put(TaskEntryContract.TaskEntry.COLUMN_NAME_SUBTITLE, task.taskPlace)


        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllTasks() : ArrayList<Task>{
        val taskArrayList = ArrayList<Task>()

        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val taskId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val taskName = cursor.getString(cursor.getColumnIndex(TaskEntryContract.TaskEntry.COLUMN_NAME_TITLE))
                val taskPlace = cursor.getString(cursor.getColumnIndex(TaskEntryContract.TaskEntry.COLUMN_NAME_SUBTITLE))
                Log.d("task gotten ", taskName)

                val task = Task(taskId, taskName, taskPlace)
                taskArrayList.add(task)

            } while (cursor.moveToNext())

        }
        cursor.close()


        return taskArrayList
    }

    fun deleteTask(taskId: String){
        // Gets the data repository in write mode
        val db = this.writableDatabase
        // Define 'where' part of query.
        val selection = BaseColumns._ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(taskId)
        // Issue SQL statement.
        db.delete(TABLE_NAME, selection, selectionArgs)

    }

    fun getRows() : Int{
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        val numberOfRows = cursor.count
        cursor.close()
        return numberOfRows
    }


    fun isInDatabase(taskId: Int) : Boolean{
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mTaskId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                if(taskId == mTaskId) return true

            } while (cursor.moveToNext())

        }
        cursor.close()
        return false

    }









    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TaskEntryContract.TaskEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TaskEntryContract.TaskEntry.COLUMN_NAME_TITLE} TEXT," +
                    "${TaskEntryContract.TaskEntry.COLUMN_NAME_SUBTITLE} TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TaskEntryContract.TaskEntry.TABLE_NAME
    }


}