package com.example.todoapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.db.DbHelper
import com.example.todoapp.dialogs.DialogBuilder
import com.example.todoapp.dialogs.DialogHelper
import com.example.todoapp.dialogs.DialogUtil
import com.example.todoapp.recycler.EndlessRecyclerViewScrollListener
import com.example.todoapp.recycler.ListAdapter
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var dbHelper: DbHelper
    private lateinit var listAdapter: ListAdapter
    private lateinit var dialogHelper: DialogHelper
    private lateinit var dialogBuilder: DialogBuilder
    private lateinit var dialogUtil: DialogUtil

    private lateinit var listEmptyTextView: TextView
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    private lateinit var progressBar: ProgressBar
    private lateinit var loadMoreProgressBar: ProgressBar
    private val stopLoadMoreProgressBar =
        Runnable { loadMoreProgressBar.visibility = ProgressBar.GONE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listEmptyTextView = findViewById(R.id.list_empty_text_view)
        progressBar = findViewById(R.id.progress_bar)
        loadMoreProgressBar = findViewById(R.id.load_more_progress_bar)
        var recyclerView = findViewById<RecyclerView>(R.id.list_todo)

        dbHelper = DbHelper(this)

        listAdapter = ListAdapter()

        val uiUpdater = UIUpdater(listAdapter, this)
        dialogUtil = DialogUtil(dbHelper, uiUpdater)
        dialogHelper =
            DialogHelper(dialogUtil, progressBar)
//            DialogHelper(progressBar, dbHelper, uiUpdater)

        val linearLayoutManager = LinearLayoutManager(this)
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadNextData(page)
            }
        }
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = listAdapter
        }

        dialogBuilder = DialogBuilder(dialogHelper)

        progressBar.visibility = ProgressBar.GONE
        loadMoreProgressBar.visibility = ProgressBar.VISIBLE

        Timer().schedule(timerTask {
            loadNextData(0)
            recyclerView.addOnScrollListener(scrollListener)
            runOnUiThread(stopLoadMoreProgressBar)
        }, 0)
    }

    fun openAddDialog(view: View) {
        val dialog = dialogBuilder.buildAddDialog(this)
        dialog.show()
    }

    fun openEditDialog(view: View) {
        val dialog = dialogBuilder.buildEditDialog(this, view)
        dialog.show()
    }

    fun openDeleteDialog(view: View) {
        val dialog = dialogBuilder.buildDeleteDialog(this, view)
        dialog.show()
    }

    private fun loadNextData(offset: Int) {
        loadMoreProgressBar.visibility = ProgressBar.VISIBLE
        Timer().schedule(timerTask {
            val taskList = dbHelper.loadPage((offset) * PAGE_SIZE, PAGE_SIZE)
            if (taskList.size > 0) {
                listAdapter.add(taskList)
                runOnUiThread { listAdapter.notifyDataSetChanged() }
            }
            runOnUiThread(stopLoadMoreProgressBar)
        }, 0)
    }

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}

