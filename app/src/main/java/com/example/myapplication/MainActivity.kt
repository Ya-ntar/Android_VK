package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.AppDimens
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppListScreen()
            }
        }
    }
}
data class AppItem(
    val id: Int,
    val iconResId: Int,
    val name: String,
    val downloads: Long,
    val isDownloaded: Boolean
)

val current_apps_list = listOf(
    AppItem(1, R.drawable.food_app, "Еда", 45_000_000L, false),
    AppItem(2, R.drawable.music_app, "Музыка", 30_000_000L, true),
    AppItem(3, R.drawable.notes_app, "Заметки", 12_000_000L, false),
    AppItem(4, R.drawable.tg_app, "Telegram", 80_000_000L, true),
    AppItem(5, R.drawable.twitter_app, "Twitter", 60_000_000L, false)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(apps: List<AppItem> = current_apps_list) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Приложения") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        AppList(
            apps = apps,
            onDownloadClick = { /* ... */ },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
fun AppList(modifier: Modifier = Modifier, apps: List<AppItem>, onDownloadClick: () -> Unit) {

    Column(modifier = modifier) {
        for (app in apps) {
            AppItemView(
                app,
                onDownloadClick = onDownloadClick,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun AppItemView(
    x0: AppItem,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppDimens.ItemPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AppDimens.IconSize)
                .clip(RoundedCornerShape(AppDimens.IconCornerRadius))
        ) {
            Image(
                painter = painterResource(id = x0.iconResId),
                contentDescription = x0.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.width(AppDimens.ItemSpacing))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = x0.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${x0.downloads} downloads",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.width(AppDimens.ItemSpacing))

        if (x0.isDownloaded) {
            Text(
                text = "Downloaded",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Button(onClick = onDownloadClick) {
                Text("Download")
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
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}