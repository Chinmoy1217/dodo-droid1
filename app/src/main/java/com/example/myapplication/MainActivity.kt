package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingWithButton("friend")
                }
            }
        }
    }
}

@Composable
fun GreetingWithButton(name: String, modifier: Modifier = Modifier) {
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Hello $name!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = { showDialog.value = true },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Show Dialog")
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Dialog Title") },
                text = { Text(text = "This is a dialog message.") },
                confirmButton = {
                    Button(
                        onClick = { showDialog.value = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingWithButtonPreview() {
    MyApplicationTheme {
        GreetingWithButton("friend")
    }
}
