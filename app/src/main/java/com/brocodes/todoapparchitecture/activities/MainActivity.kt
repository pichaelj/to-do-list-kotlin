package com.brocodes.todoapparchitecture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.todoapparchitecture.R
import com.brocodes.todoapparchitecture.adapters.RecyclerViewAdapter
import com.brocodes.todoapparchitecture.model.Task
import com.brocodes.todoapparchitecture.sqllitedatabaseutils.TaskDBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var dbHelper: TaskDBHelper
    private lateinit var taskList: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = TaskDBHelper(this)

        val addNoteButton: Button = findViewById(R.id.add_note_button)
        addNoteButton.setOnClickListener { addNote() }

        val deleteNoteButton: ImageButton = findViewById(R.id.delete_button)
        deleteNoteButton.setOnClickListener { deleteNote() }

        linearLayoutManager = LinearLayoutManager(this)

        val notesRecyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        notesRecyclerView.layoutManager = linearLayoutManager

        taskList = dbHelper.getAllTasks()
        recyclerViewAdapter = RecyclerViewAdapter(taskList)
        notesRecyclerView.adapter = recyclerViewAdapter

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
                val task = Task(null, taskName, taskPlace)

                try {

                    dbHelper.addTask(task)
                    taskList.add(task)
                    recyclerViewAdapter.notifyDataSetChanged()

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

    private fun deleteNote(){
        val deleteNoteById: EditText = findViewById(R.id.delete_by_id)
        when {
            deleteNoteById.text.toString().isBlank() -> {
                deleteNoteById.requestFocus()
                deleteNoteById.error = "Please enter the task Id you wish to delete"

            }
            !dbHelper.isInDatabase(deleteNoteById.text.toString().toInt()) -> {
                deleteNoteById.requestFocus()
                deleteNoteById.error = "Please enter valid task id to continue"
            }
            else -> {
                val taskId = deleteNoteById.text.toString()
                dbHelper.deleteTask(taskId)
                recyclerViewAdapter.removeItem(taskId.toInt())

            }

        }
    }

}
