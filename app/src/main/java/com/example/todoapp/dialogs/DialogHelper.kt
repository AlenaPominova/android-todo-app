package com.example.todoapp.dialogs

import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import com.example.todoapp.IUIUpdater
import com.example.todoapp.UIUpdater
import com.example.todoapp.db.IDbHelper
import com.example.todoapp.db.ToDo
import java.util.*
import kotlin.concurrent.timerTask

class DialogHelper(
    private val dialogUtil: DialogUtil,
    private val progressBar: ProgressBar
//    private val dbHelper: IDbHelper,
//    private val uiUpdater: IUIUpdater
) {
    private val stopProgressBar = Runnable { progressBar.visibility = ProgressBar.GONE }

    fun saveDialogAction(dialog: AlertDialog, taskEditText: EditText, exceptionTextView: TextView) {
        val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.isEnabled = false
        taskEditText.doAfterTextChanged {
            button.isEnabled = taskEditText.text.isNotEmpty()
        }

        button.setOnClickListener {
            val todo = ToDo(0, taskEditText.text.toString())
            progressBar.visibility = ProgressBar.VISIBLE
            Timer().schedule(timerTask {
                try {
                    exceptionTextView.text = ""

                    dialogUtil.saveAction(todo, stopProgressBar)
//                    dbHelper.add(todo)
//                    uiUpdater.addToDo(todo)
//                    uiUpdater.runOnUiThread(stopProgressBar)
//
//                    //Dismiss once everything is OK.
                    dialog.dismiss()
                } catch (e: Exception) {
                    exceptionTextView.text = e.message
                }
            }, 0)
        }
    }

    fun editDialogAction(
        dialog: AlertDialog,
        taskEditText: EditText,
        exceptionTextView: TextView,
        originalTask: ToDo
    ) {
        val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            originalTask.task = taskEditText.text.toString()
            progressBar.visibility = ProgressBar.VISIBLE
            Timer().schedule(timerTask {
                try {
                    exceptionTextView.text = ""

                    dialogUtil.editAction(originalTask, stopProgressBar)
//                    dbHelper.update(originalTask)
//                    uiUpdater.updateToDo(originalTask)
//                    uiUpdater.runOnUiThread(stopProgressBar)
//
//                    //Dismiss once everything is OK.
                    dialog.dismiss()
                } catch (e: Exception) {
                    exceptionTextView.text = e.message
                }
            }, 0)
        }
    }

    fun deleteDialogAction(dialog: AlertDialog, task: Int) {
        val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            progressBar.visibility = ProgressBar.VISIBLE
            Timer().schedule(timerTask {
                try {
                    dialogUtil.deleteAction(task, stopProgressBar)
//                    dbHelper.delete(task)
//                    uiUpdater.removeToDo(task)
//                    uiUpdater.runOnUiThread(stopProgressBar)
//
//                    //Dismiss once everything is OK.
                    dialog.dismiss()
                } catch (e: Exception) {
                    //ignore
                }
            }, 0)
        }
    }
}