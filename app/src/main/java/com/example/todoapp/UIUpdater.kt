package com.example.todoapp

import com.example.todoapp.db.ToDo
import com.example.todoapp.recycler.IListAdapter
import com.example.todoapp.recycler.ListAdapter

interface IUIUpdater {
    fun runOnUiThread(action: Runnable)
    fun addToDo(todo: ToDo)
    fun removeToDo(id: Int)
    fun updateToDo(todo: ToDo)
}

class UIUpdater(private val listAdapter: IListAdapter, private val mainActivity: IMainActivity) :
    IUIUpdater {
    override fun runOnUiThread(action: Runnable){
        mainActivity.runOnUiThread(action)
    }

    override fun addToDo(todo: ToDo) {
        listAdapter.addFirst(todo)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemInserted(0)
            }
        )
    }

    override fun removeToDo(id: Int) {
        val index = listAdapter.remove(id)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemRemoved(index)
            }
        )
    }

    override fun updateToDo(todo: ToDo) {
        val index = listAdapter.remove(todo.id)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemRemoved(index)
            }
        )

        listAdapter.addFirst(todo)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemInserted(0)
            }
        )
    }
}