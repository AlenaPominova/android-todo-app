package com.example.todoapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.db.ToDo

interface IListAdapter {
    fun add(todoList: ArrayList<ToDo>)
    fun addFirst(todo: ToDo)
    fun remove(id: Int): Int
    fun update(todo: ToDo): Int
    fun clear()
    fun notifyItemInserted(position: Int)
    fun notifyItemRemoved(position: Int)
}

class TaskListAdapter: RecyclerView.Adapter<ViewHolder>(), IListAdapter {

    private val list = ArrayList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ToDo = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    override fun add(todoList: ArrayList<ToDo>) {
        list.addAll(todoList)
    }

    override fun addFirst(todo: ToDo) {
        list.add(0, todo)
    }

    override fun remove(id: Int): Int {
        val el = list.first { o -> o.id == id }
        val index = list.indexOf(el)
        list.remove(el)
        return index
    }

    override fun update(todo: ToDo): Int {
        val el = list.first { o -> o.id == todo.id }
        val index = list.indexOf(el)
        el.task = todo.task
        list.remove(el)
        list.add(0, el)
        return  index
    }

    override fun clear() {
        list.clear()
    }

    fun getAllTasks(): ArrayList<ToDo> {
        return list
    }
}

class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_todo, parent, false)) {

    private var taskView: TextView? = null
    private var idView: TextView? = null

    init {
        taskView = itemView.findViewById(R.id.task_title)
        idView = itemView.findViewById(R.id.task_id)
    }

    fun bind(todo: ToDo) {
        idView?.text = todo.id.toString()
        taskView?.text = todo.task
    }
}