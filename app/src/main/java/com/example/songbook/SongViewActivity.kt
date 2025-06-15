package com.example.songbook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun ViewScreen(
    viewModel: SongViewModel,
    songId: Int,
    onEditClicked: (Int) -> Unit,
    onDeleteClicked: () -> Unit,
    onBack: () -> Unit
) {
    val song = viewModel.songs.collectAsState().value.find { it.id == songId }

    if (song == null) {
        Text("Not found")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // <- enable scroll
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = song.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(text = song.chords, fontWeight = FontWeight.Bold)
        Text(text = "Strojenie: ${song.tuning}", fontWeight = FontWeight.Bold)
        Text(text = "Kapodaster: ${song.capo}", fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))
        LyricsWithChords(lyrics = song.lyrics)

        Spacer(Modifier.height(16.dp))
        Button(onClick = { onEditClicked(songId) }) {
            Text("Edytuj")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Wróć")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.deleteSong(songId)
                onDeleteClicked()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Usuń")
        }
    }
}

@Composable
fun LyricsWithChords(lyrics: String) {
    val parsed = parseLyrics(lyrics)

    Text(text = parsed, color = MaterialTheme.colorScheme.onSurface)
}

@Composable
fun parseLyrics(lyrics: String): AnnotatedString {
    val builder = AnnotatedString.Builder()

    val pattern = "\\[([^]]+)]".toRegex()
    var lastIndex = 0

    for (match in pattern.findAll(lyrics)) {
        val marked_text = match.groups[1]?.value ?: ""

        builder.append(lyrics.substring(lastIndex, match.range.first))

        builder.pushStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
        builder.append("$marked_text")
        builder.pop()
        lastIndex = match.range.last + 1
    }
    builder.append(lyrics.substring(lastIndex))

    return builder.toAnnotatedString()
}

