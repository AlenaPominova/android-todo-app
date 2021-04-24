package com.example.todoapp

import com.example.todoapp.db.ToDo
import com.example.todoapp.recycler.IListAdapter

interface IUIUpdater {
    fun runOnUiThread(action: Runnable)
    fun addToDo(todo: ToDo)
    fun removeToDo(id: Int)
    fun updateToDo(todo: ToDo)
}

class UIUpdater(
    private val taskListAdapter: IListAdapter,
    private val mainActivity: IMainActivity
) :

    IUIUpdater {
    override fun runOnUiThread(action: Runnable) {
        mainActivity.runOnUiThread(action)
    }

    override fun addToDo(todo: ToDo) {
        taskListAdapter.addFirst(todo)
        mainActivity.runOnUiThread(
            Runnable {
                taskListAdapter.notifyItemInserted(0)
            }
        )
    }

    override fun removeToDo(id: Int) {
        val index = taskListAdapter.remove(id)
        mainActivity.runOnUiThread(
            Runnable {
                taskListAdapter.notifyItemRemoved(index)
            }
        )
    }

    override fun updateToDo(todo: ToDo) {
        val index = taskListAdapter.remove(todo.id)
        mainActivity.runOnUiThread(
            Runnable {
                taskListAdapter.notifyItemRemoved(index)
            }
        )

        taskListAdapter.addFirst(todo)
        mainActivity.runOnUiThread(
            Runnable {
                taskListAdapter.notifyItemInserted(0)
            }
        )
    }
}