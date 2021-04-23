package com.example.todoapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.db.ToDo

class ListAdapter: RecyclerView.Adapter<ViewHolder>() {

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

    fun add(todoList: ArrayList<ToDo>) {
        list.addAll(todoList)
    }

    fun addFirst(todo: ToDo) {
        list.add(0, todo)
    }

    fun remove(id: Int): Int {
        val el = list.first { o -> o.id == id }
        val index = list.indexOf(el)
        list.remove(el)
        return index
    }

    fun update(todo: ToDo): Int {
        val el = list.first { o -> o.id == todo.id }
        val index = list.indexOf(el)
        el.task = todo.task
        list.remove(el)
        list.add(0, el)
        return  index
    }

    fun clear() {
        list.clear()
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