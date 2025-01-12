package com.example.tasktidy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.tasktidy.ui.theme.TaskTidyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskTidyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskTidyApp()
                }
            }
        }
    }
}

@Composable
fun TaskTidyApp() {
    var taskList by remember { mutableStateOf(mutableListOf<String>()) }
    var newTask by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "TaskTidy",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input field and Add button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .background(Color.LightGray, MaterialTheme.shapes.small)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (newTask.text.isEmpty()) {
                        Text(text = "Add a new task", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
            Button(
                onClick = {
                    if (newTask.text.isNotBlank()) {
                        taskList.add(newTask.text)
                        newTask = TextFieldValue("")
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task list
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(taskList.size) { index ->
                TaskItem(
                    task = taskList[index],
                    onRemove = {
                        taskList.removeAt(index)
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: String, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(task, style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = onRemove,
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("Remove", color = Color.White)
        }
    }
}