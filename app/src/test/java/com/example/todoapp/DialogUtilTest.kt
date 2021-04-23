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
    fun verifyDdDeleteMethodWasInvokedOnce() {
        dialogUtil = DialogUtil(dbHelper, uiUpdater)
        dialogUtil.deleteAction(1, stopProgressBar)

        verify(dbHelper, times(1)).delete(1)
    }
}