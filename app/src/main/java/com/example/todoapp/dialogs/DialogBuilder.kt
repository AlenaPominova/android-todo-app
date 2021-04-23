package com.example.todoapp.dialogs

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.todoapp.R
import com.example.todoapp.R.string
import com.example.todoapp.db.ToDo


class DialogBuilder(val dialogHelper: DialogHelper) {

    fun buildAddDialog(context: Context): AlertDialog {
        val taskEditText = EditText(context)
        val exceptionTextView = TextView(context)
        exceptionTextView.setTextColor(context.resources.getColor(R.color.red))

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(taskEditText)
        linearLayout.addView(exceptionTextView)
        linearLayout.setPadding(50, 0, 50, 0)

        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.add_task))
            .setView(linearLayout)
            .setPositiveButton(context.getString(R.string.add)) { _, _ -> }
            .setNegativeButton(context.getString(R.string.cancel), null)
            .create()

        dialog.setOnShowListener {
            dialogHelper.saveDialogAction(dialog, taskEditText, exceptionTextView)
        }

        return dialog
    }

    fun buildEditDialog(context: Context, view: View): AlertDialog {
        val parent = view.parent as View
        val taskTextView = parent.findViewById<TextView>(R.id.task_title)
        val taskIdView = parent.findViewById<TextView>(R.id.task_id)
        val originalTask = ToDo(taskIdView.text.toString().toInt(), taskTextView.text.toString())

        val taskEditText = EditText(context)
        taskEditText.setText(originalTask.task)
        val exceptionTextView = TextView(context)
        exceptionTextView.setTextColor(context.resources.getColor(R.color.red))

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(taskEditText)
        linearLayout.addView(exceptionTextView)
        linearLayout.setPadding(50, 0, 50, 0)

        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(string.edit))
            .setView(linearLayout)
            .setPositiveButton(context.getString(string.save)) { _, _ -> }
            .setNegativeButton(string.cancel, null)
            .create()

        dialog.setOnShowListener {
            dialogHelper.editDialogAction(dialog, taskEditText, exceptionTextView, originalTask)
        }

        return dialog
    }

    fun buildDeleteDialog(context: Context, view: View): AlertDialog {
        val parent = view.parent as View
        val taskIdView = parent.findViewById<TextView>(R.id.task_id)
        val id = taskIdView.text.toString().toInt()

        val textView = TextView(context)
        textView.text = context.getString(string.confirm_delete)
        textView.setPadding(50, 0, 50, 0)

        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(string.remove))
            .setView(textView)
            .setPositiveButton(context.getString(string.remove)) { _, _ -> }
            .setNegativeButton(string.cancel, null)
            .create()

        dialog.setOnShowListener {
            dialogHelper.deleteDialogAction(dialog, id)
        }

        return dialog
    }
}