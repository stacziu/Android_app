package com.example.songbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp


@Composable
fun EditSongScreen(
    viewModel: SongViewModel,
    songId: Int,
    onSongUpdated: () -> Unit,
    onBack: () -> Unit
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
            label = { Text("Tytuł") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = chords,
            onValueChange = { chords = it },
            label = { Text("Chwyty") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lyrics,
            onValueChange = { lyrics = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            singleLine = false,
            maxLines = 10,
            label = { Text("Tekst") },
            placeholder = { Text("Zapisz tekst piosenki a elementy, które chcesz wyróżnić zapisz w []") },
        )
        OutlinedTextField(
            value = tuning,
            onValueChange = { tuning = it },
            label = { Text("Strojenie") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = capo,
            onValueChange = { capo = it },
            label = { Text("Kapodaster") },
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
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}

