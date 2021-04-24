package com.example.todoapp.dialogs

import android.widget.TextView
import com.example.todoapp.IUIUpdater
import com.example.todoapp.db.IDbHelper
import com.example.todoapp.db.ToDo

class DialogUtil(
    private val dbHelper: IDbHelper,
    private val uiUpdater: IUIUpdater
) {

    fun saveAction(todo: ToDo, stopProgressBar: Runnable) {
        dbHelper.add(todo)
        uiUpdater.addToDo(todo)
        uiUpdater.runOnUiThread(stopProgressBar)
    }

    fun editAction(originalTask: ToDo, stopProgressBar: Runnable
    ) {
        dbHelper.update(originalTask)
        uiUpdater.updateToDo(originalTask)
        uiUpdater.runOnUiThread(stopProgressBar)
    }

    fun deleteAction(task: Int, stopProgressBar: Runnable) {
        dbHelper.delete(task)
        uiUpdater.removeToDo(task)
        uiUpdater.runOnUiThread(stopProgressBar)
    }
}