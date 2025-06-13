package com.example.songbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.songbook.ui.theme.SongbookTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                Surface {
                    NavHost(navController = navController, startDestination = Routes.SONG_LIST) {

                        composable(Routes.SONG_LIST) {
                            SongListScreen(
                                viewModel = viewModel,
                                onAddSongClicked = { navController.navigate(Routes.ADD_SONG) },
                                onViewSongClicked = { songId ->
                                    navController.navigate("view_song/$songId")
                                }
                            )
                        }

                        composable(Routes.ADD_SONG) {
                            AddSongScreen(
                                viewModel = viewModel,
                                onSongAdded = {
                                    navController.popBackStack() // go back after adding
                                }
                            )
                        }

                        composable("edit_song/{songId}") { backStackEntry ->
                            val songId = backStackEntry.arguments?.getString("songId")?.toIntOrNull()
                            if (songId == null) {
                                Text("Invalid songId")
                            } else {
                                EditSongScreen(
                                    viewModel = viewModel,
                                    songId = songId,
                                    onSongUpdated = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        composable("view_song/{songId}") { backStackEntry ->
                            val songId = backStackEntry.arguments?.getString("songId")?.toIntOrNull()
                            if (songId == null) {
                                Text("Invalid songId")
                            } else {
                                ViewScreen(
                                    viewModel = viewModel,
                                    songId = songId,
                                    onEditClicked = { id ->
                                        navController.navigate("edit_song/$id")
                                    },
                                    onBack = {
                                        navController.popBackStack()
                                    },
                                    onDeleteClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SongListScreen(
    viewModel: SongViewModel,
    onAddSongClicked: () -> Unit,
    onViewSongClicked: (Int) -> Unit
) {
    val songs by viewModel.songs.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(songs) { song ->
                song.id?.let { id ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onViewSongClicked(id) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = song.title, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddSongClicked,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Song")
        }
    }
}