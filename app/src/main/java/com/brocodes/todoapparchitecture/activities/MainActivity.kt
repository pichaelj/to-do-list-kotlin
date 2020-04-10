package com.brocodes.todoapparchitecture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.todoapparchitecture.R
import com.brocodes.todoapparchitecture.adapters.RecyclerViewAdapter
import com.brocodes.todoapparchitecture.model.Task
import com.brocodes.todoapparchitecture.sqllitedatabaseutils.TaskDBHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var dbHelper: TaskDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = TaskDBHelper(this)

        val addNoteButton: Button = findViewById(R.id.add_note_button)
        addNoteButton.setOnClickListener { addNote() }

        linearLayoutManager = LinearLayoutManager(this)

        val notesRecyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        notesRecyclerView.layoutManager = linearLayoutManager

        val taskList = dbHelper.getAllTasks()
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
