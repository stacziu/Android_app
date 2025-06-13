package com.example.songbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun AddSongScreen(
    onSongAdded: () -> Unit,
    viewModel: SongViewModel
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var chords by remember { mutableStateOf(TextFieldValue()) }
    var lyrics by remember { mutableStateOf(TextFieldValue()) }
    var tuning by remember { mutableStateOf(TextFieldValue()) }
    var capo by remember { mutableStateOf(TextFieldValue()) }

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
                val song = Song(
                    title = title.text,
                    chords = chords.text,
                    lyrics = lyrics.text,
                    tuning = tuning.text,
                    capo = capo.text
                )
                viewModel.addSong(song)
                onSongAdded()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}