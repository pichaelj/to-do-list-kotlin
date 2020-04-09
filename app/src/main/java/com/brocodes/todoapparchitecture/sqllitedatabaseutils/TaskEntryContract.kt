package com.brocodes.todoapparchitecture.sqllitedatabaseutils

import android.provider.BaseColumns

object TaskEntryContract {
    class TaskEntry: BaseColumns {

        companion object{
            const val TABLE_NAME = "tasks"
            const val COLUMN_NAME_TITLE = "task_name"
            const val COLUMN_NAME_SUBTITLE = "task_place"
        }


    }


}