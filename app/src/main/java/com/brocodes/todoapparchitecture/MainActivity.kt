package com.brocodes.todoapparchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.brocodes.todoapparchitecture.model.Task
import com.brocodes.todoapparchitecture.sqllitedatabaseutils.TaskDBHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addNoteButton: Button = findViewById(R.id.add_note_button)
        addNoteButton.setOnClickListener { addNote() }
    }

    private fun addNote(){
        val taskNameEditText: EditText = findViewById(R.id.addTask)
        val taskPlaceEditText: EditText = findViewById(R.id.addPlace)

        when {
            taskNameEditText.text.toString().isBlank() -> {
                taskNameEditText.requestFocus()
                taskNameEditText.error = "Please Enter a Task"
            }
            taskPlaceEditText.text.toString().isBlank() -> {
                taskNameEditText.requestFocus()
                taskPlaceEditText.error = "Please enter the place of task"
            }
            else -> {

                val taskName = taskNameEditText.text.toString().trim()
                val taskPlace = taskPlaceEditText.text.toString().trim()
                val task = Task(taskName, taskPlace)

                try {
                    val dbHelper = TaskDBHelper(this)
                    dbHelper.addTask(task)
                    taskNameEditText.setText("")
                    taskPlaceEditText.setText("")
                } catch (e: Exception) {
                    val errorMessage = "Couldn't add Task, Please Try again"
                    val errorToast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
                    errorToast.show()
                    Log.e("sqllite exception", e.message.orEmpty())


                }


            }
        }
    }

}
