package com.example.composetodo.data

object DataCenter {
    val todos = mutableListOf(
        Todo().apply {
            name = "吃飯"
        },
        Todo().apply {
            name = "睡覺"
        },
        Todo().apply {
            name = "打東東"
        }
    )
}