package com.example.todoapp

import com.example.todoapp.db.ToDo
import com.example.todoapp.recycler.TaskListAdapter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TaskListAdapterTest {

    @Test
    fun checkAddElement() {
        val listAdapter = TaskListAdapter()

        val expectedTodo = ToDo(1, "First tsk to add")
        listAdapter.addFirst(expectedTodo)
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assertEquals(expectedTodo, actual.get(0))
    }

    @Test
    fun checkAddNewElementFirst() {
        val listAdapter = TaskListAdapter()

        val expectedTodo = ToDo(1, "First tsk to add")
        val secondTodo = ToDo(1, "Second tsk to add")
        listAdapter.addFirst(secondTodo)
        listAdapter.addFirst(expectedTodo)
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assertEquals(expectedTodo, actual.get(0))
    }

    @Test
    fun checkAddAll() {
        val listAdapter = TaskListAdapter()

        val firstTodo = ToDo(1, "First tsk to add")
        val secondTodo = ToDo(1, "Second tsk to add")

        val expected = ArrayList<ToDo>()
        expected.add(firstTodo)
        expected.add(secondTodo)

        listAdapter.add(expected)
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun checkRemoveElementByIndex() {
        val listAdapter = TaskListAdapter()

        val firstTodo = ToDo(1, "First tsk to add")
        val secondTodo = ToDo(2, "Second tsk to add")
        val expected = ToDo(3, "Expected tsk to add")

        val taskList = ArrayList<ToDo>()
        taskList.add(firstTodo)
        taskList.add(expected)
        taskList.add(secondTodo)

        listAdapter.add(taskList)
        listAdapter.remove(firstTodo.id)
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assertEquals(expected, actual.get(0))
    }

    @Test
    fun checkUpdatedElementIsOnTop() {
        val listAdapter = TaskListAdapter()

        val firstTodo = ToDo(1, "First tsk to add")
        val secondTodo = ToDo(2, "Second tsk to add")
        val thirdTodo = ToDo(3, "thirdTodo tsk to add")
        val expected = ToDo(2, "Second updated tsk")

        val taskList = ArrayList<ToDo>()
        taskList.add(firstTodo)
        taskList.add(secondTodo)
        taskList.add(thirdTodo)

        listAdapter.add(taskList)
        listAdapter.update(expected)
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assertEquals(expected, actual.get(0))
    }

    @Test
    fun checkListSizeDoesntCHangeAFterUpdateElement() {
        val listAdapter = TaskListAdapter()

        val firstTodo = ToDo(1, "First tsk to add")
        val secondTodo = ToDo(2, "Second tsk to add")
        val thirdTodo = ToDo(3, "thirdTodo tsk to add")
        val expected = ToDo(2, "Second updated tsk")

        val taskList = ArrayList<ToDo>()
        taskList.add(firstTodo)
        taskList.add(secondTodo)
        taskList.add(thirdTodo)

        listAdapter.add(taskList)
        listAdapter.update(expected)
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assertEquals(taskList.size, actual.size)
    }

    @Test
    fun checkCLearTaskList() {
        val listAdapter = TaskListAdapter()

        val firstTodo = ToDo(1, "First tsk to add")
        val secondTodo = ToDo(2, "Second tsk to add")
        val thirdTodo = ToDo(3, "thirdTodo tsk to add")

        val taskList = ArrayList<ToDo>()
        taskList.add(firstTodo)
        taskList.add(secondTodo)
        taskList.add(thirdTodo)

        listAdapter.add(taskList)
        listAdapter.clear()
        val actual = listAdapter.getAllTasks()

        assertNotNull(actual)
        assert(actual.isEmpty())
    }
}