package com.example.todoapp

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.example.todoapp.db.DbHelper
import com.example.todoapp.db.IDbHelper
import com.example.todoapp.db.ToDo
import com.example.todoapp.dialogs.DialogHelper
import com.example.todoapp.dialogs.DialogUtil
import com.example.todoapp.recycler.IListAdapter
import com.example.todoapp.recycler.ListAdapter
import junit.framework.Assert.*
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
public class DialogUtilTest {

    @Mock
    lateinit var dbHelper: IDbHelper

    @Mock
    lateinit var uiUpdater: IUIUpdater

    @Mock
    lateinit var stopProgressBar: Runnable

    lateinit var dialogUtil: DialogUtil

    @Test
    fun checkMockObjectsAreNotNull() {
        assertNotNull(dbHelper)
        assertNotNull(uiUpdater)
    }

    @Test
    fun verifyDeleteMethodWasInvokedOnce() {
        dialogUtil = DialogUtil(dbHelper, uiUpdater)
        dialogUtil.deleteAction(1, stopProgressBar)

        val todo = ToDo(1, "New task")

        verify(dbHelper, times(1)).delete(1)
        verify(uiUpdater, times(1)).removeToDo(1)
        verify(uiUpdater, never()).addToDo(todo)
    }

    @Test
    fun verifyUpdateWasInvokedOnce() {
        dialogUtil = DialogUtil(dbHelper, uiUpdater)
        val todo = ToDo(1, "New task for Update")
        dialogUtil.editAction(todo, stopProgressBar)

        verify(dbHelper, times(1)).update(todo)
        verify(uiUpdater, times(1)).updateToDo(todo)
        verify(uiUpdater, never()).removeToDo(ArgumentMatchers.anyInt())
    }

    @Test
    fun verifyAddWasInvokedOnce() {
        dialogUtil = DialogUtil(dbHelper, uiUpdater)
        val todo = ToDo(1, "New task for Save")
        dialogUtil.saveAction(todo, stopProgressBar)

        verify(dbHelper, times(1)).add(todo)
        verify(uiUpdater, times(1)).addToDo(todo)
        verify(uiUpdater, never()).updateToDo(todo)
    }
}