package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val editText = remember {
        EditText(context).apply {
            hint = "Введите текст"
        }
    }

    Column(modifier = modifier) {
        Greeting(name = "Android")

        AndroidView(
            factory = { editText },
            modifier = Modifier.fillMaxWidth()
        )

        OpenSecondActivityButton(
            onClick = {
                val intent = Intent(context, SecondActivity::class.java)
                intent.putExtra(SecondActivity.TEXT_KEY, editText.text.toString())
                context.startActivity(intent)
            }
        )

        CallFriendButton(
            phoneNumber = { editText.text.toString() }
        )
    }
}

@Composable
fun OpenSecondActivityButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Открыть вторую Activity")
    }
}

@Composable
fun CallFriendButton(phoneNumber: () -> String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${phoneNumber()}")
            )
            context.startActivity(intent)
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Позвонить другу")
    }
}