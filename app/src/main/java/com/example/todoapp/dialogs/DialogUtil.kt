package com.example.todoapp.dialogs

import android.widget.TextView
import com.example.todoapp.IUIUpdater
import com.example.todoapp.db.IDbHelper
import com.example.todoapp.db.ToDo

class DialogUtil(
    private val dbHelper: IDbHelper,
    private val uiUpdater: IUIUpdater
) {

    fun saveAction(exceptionTextView: TextView,
                   todo: ToDo, stopProgressBar: Runnable) {
        exceptionTextView.text = ""

        dbHelper.add(todo)
        uiUpdater.addToDo(todo)
        uiUpdater.runOnUiThread(stopProgressBar)
    }

    fun editAction(exceptionTextView: TextView,
                   originalTask: ToDo, stopProgressBar: Runnable
    ) {
        exceptionTextView.text = ""

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