package com.brocodes.todoapparchitecture.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.todoapparchitecture.R
import com.brocodes.todoapparchitecture.model.Task
import kotlinx.android.synthetic.main.note_item.view.*

class RecyclerViewAdapter(private val tasks: ArrayList<Task>) : RecyclerView.Adapter<RecyclerViewAdapter.TaskItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)

        return TaskItemHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskItemHolder, position: Int) {
        holder.bind(tasks[position])
    }

    fun removeItem(position: Int) {
        tasks.removeAt(position - 1)
        notifyItemRangeChanged(position, tasks.size)
    }



    //Make the class extend RecyclerView.ViewHolder, allowing the adapter to use it as as a ViewHolder.
    class TaskItemHolder(v: View) : RecyclerView.ViewHolder(v){

        /*Add a reference to the view you’ve inflated to allow
        the ViewHolder to access the ImageView and TextView as an extension property.*/

        private val idView = v.task_id
        private val taskName = v.task_name
        private val taskPlace = v.task_place

        fun bind(task : Task) {
            idView.text = task.taskId.toString()
            taskName.text = task.task.capitalize()
            taskPlace.text = task.taskPlace
        }



    }
}