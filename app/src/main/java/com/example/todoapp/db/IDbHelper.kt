package com.example.todoapp.db

interface IDbHelper {
    fun add(todo: ToDo)
    fun update(todo: ToDo)
    fun delete(id: Int)
    fun loadPage(offset: Int, limit: Int): ArrayList<ToDo>
}