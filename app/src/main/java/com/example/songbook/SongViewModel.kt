package com.example.songbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = MyDatabaseHelper(application)

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs : StateFlow<List<Song>> = _songs

    init {
        loadSongs()
    }

    fun loadSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            val allSongs = dbHelper.getAllSongs()
            _songs.value = allSongs
        }
    }

    fun addSong(newSong : Song) {
        viewModelScope.launch(Dispatchers.IO){
            dbHelper.insertSong(newSong)
            loadSongs()
        }
    }

    fun updateSong(editedSong : Song) {
        viewModelScope.launch(Dispatchers.IO){
            dbHelper.updateSong(editedSong)
            loadSongs()
        }
    }

    fun deleteSong(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteSong(id)
            loadSongs()
        }
    }
}