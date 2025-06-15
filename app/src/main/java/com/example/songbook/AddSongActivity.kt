package com.example.songbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddSongScreen(
    onSongAdded: () -> Unit,
    viewModel: SongViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var chords by remember { mutableStateOf(TextFieldValue()) }
    var lyrics by remember { mutableStateOf(TextFieldValue()) }
    var tuning by remember { mutableStateOf(TextFieldValue()) }
    var capo by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current

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
            label = { Text("Tekst") },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            singleLine = false,
            maxLines = 10,
            placeholder = { Text("Zapisz tekst piosenki a elementy, które chcesz wyróżnić zapisz w [ ]") }
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
                val song = Song(
                    title = title.text,
                    chords = chords.text,
                    lyrics = lyrics.text,
                    tuning = tuning.text,
                    capo = capo.text
                )
                if (song.title.isBlank() || song.chords.isBlank() || song.lyrics.isBlank()) {
                    Toast.makeText(context, "Wypełnij wszystkie wymagane pola: Tytuł, Chwyty, Tekst", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                viewModel.addSong(song)
                onSongAdded()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Zapisz")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}