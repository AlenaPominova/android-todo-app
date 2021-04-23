package com.example.todoapp

import com.example.todoapp.db.ToDo
import com.example.todoapp.recycler.ListAdapter

class UIUpdater(private val listAdapter: ListAdapter, private val mainActivity: IMainActivity) {
    fun runOnUiThread(action: Runnable){
        mainActivity.runOnUiThread(action)
    }

    fun addToDo(todo: ToDo) {
        listAdapter.addFirst(todo)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemInserted(0)
            }
        )
    }

    fun removeToDo(id: Int) {
        val index = listAdapter.remove(id)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemRemoved(index)
            }
        )
    }

    fun updateToDo(todo: ToDo) {
        val index = listAdapter.update(todo)
        mainActivity.runOnUiThread(
            Runnable {
                listAdapter.notifyItemRemoved(index)
                listAdapter.notifyItemInserted(0)
            }
        )
    }
}