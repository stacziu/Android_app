package com.example.songbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun EditSongScreen(
    viewModel: SongViewModel,
    songId: Int,
    onSongUpdated: () -> Unit
) {
    val song = viewModel.songs.collectAsState().value.find { it.id == songId }

    if (song == null) {
        Text("Not found")
        return
    }

    var title by remember { mutableStateOf(TextFieldValue(song.title)) }
    var chords by remember { mutableStateOf(TextFieldValue(song.chords)) }
    var lyrics by remember { mutableStateOf(TextFieldValue(song.lyrics)) }
    var tuning by remember { mutableStateOf(TextFieldValue(song.tuning)) }
    var capo by remember { mutableStateOf(TextFieldValue(song.capo)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = chords,
            onValueChange = { chords = it },
            label = { Text("Chords") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lyrics,
            onValueChange = { lyrics = it },
            label = { Text("Lyrics") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            singleLine = false,
            maxLines = 10
        )
        OutlinedTextField(
            value = tuning,
            onValueChange = { tuning = it },
            label = { Text("Tuning") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = capo,
            onValueChange = { capo = it },
            label = { Text("Capo") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.updateSong(
                    song.copy(
                        title = title.text,
                        chords = chords.text,
                        lyrics = lyrics.text,
                        tuning = tuning.text,
                        capo = capo.text
                    )
                )
                onSongUpdated()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}

