package com.example.todoapp

import com.example.todoapp.db.IDbHelper
import com.example.todoapp.db.ToDo
import com.example.todoapp.dialogs.DialogUtil
import junit.framework.Assert.*
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

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