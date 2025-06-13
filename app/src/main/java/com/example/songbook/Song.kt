package com.example.songbook

data class Song(
    val id: Int? = null,
    val title: String,
    val chords: String,
    val lyrics: String,
    val tuning: String,
    val capo: String
)