package com.example.composetodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodo.data.DataCenter
import com.example.composetodo.data.Todo
import com.example.composetodo.ui.theme.ComposeTodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // 進入『Compose』模式
            ComposeTodoTheme { // lambda
                MainScreen()
            }
        }
    }

}

@Composable
private fun MainScreen() {
    var inputting: Boolean by remember { mutableStateOf(false) }
    val animatedFebScale by animateFloatAsState(if (inputting) 0f else 1f)
    val animatedInputScale by animateFloatAsState(if (inputting) 1f else 0f)
    Scaffold(
        floatingActionButton = {
//            if (!inputting) {
            FloatingActionButton(onClick = {
                // 彈出輸入框
                inputting = true
            }, Modifier.scale(animatedFebScale)) {
                Icon(Icons.Filled.Add, "添加")
            }
//            }
        },
        topBar = {
            TopAppBar {
                Icon(Icons.Filled.Check, "標題")
            }
        }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Todos(DataCenter.todos)
//            if (inputting) {
            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .scale(animatedInputScale)
            ) {
                TextField(value = "", onValueChange = {}, Modifier.weight(1f))
                IconButton(onClick = {
                    //關閉輸入框
                    inputting = false
                }) {
                    Icon(Icons.Filled.Send, contentDescription = "確認")
                }
            }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreView() {
    MainScreen()
}

@Composable
private fun Todos(todos: List<Todo>) {
    //elevation 高度 陰影
    Surface(Modifier.padding(16.dp), elevation = 8.dp, shape = RoundedCornerShape(8.dp)) {
        //RecycleView
        //Unidirectional Data Flow 單向數據流
        LazyColumn(Modifier.fillMaxWidth()) {
            items(todos) { todo ->
                // LinearLayout
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = todo.complete, onCheckedChange = { check ->
                        todo.complete = check
                    })
                    Text(text = todo.name)
                }
                // Material Design
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodosPreView() {
    val todos = listOf(
        Todo().apply {
            name = "吃飯"
        },
        Todo().apply {
            name = "睡覺"
        }
    )

    Todos(todos)
}