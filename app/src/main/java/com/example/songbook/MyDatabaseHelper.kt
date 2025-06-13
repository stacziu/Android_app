package com.example.songbook

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "songsApp.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "songs"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CHORDS = "chords"
        const val COLUMN_LYRICS = "lyrics"
        const val COLUMN_TUNING = "tuning"
        const val COLUMN_CAPO = "capo"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME(
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TITLE TEXT,
            $COLUMN_CHORDS TEXT,
            $COLUMN_LYRICS TEXT,
            $COLUMN_TUNING TEXT,
            $COLUMN_CAPO TEXT
            )
            """.trimIndent()

            db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertSong(song: Song) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, song.title)
            put(COLUMN_CHORDS, song.chords)
            put(COLUMN_LYRICS, song.lyrics)
            put(COLUMN_TUNING, song.tuning)
            put(COLUMN_CAPO, song.capo)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllSongs(): List<Song> {
        val songsList = mutableListOf<Song>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_TITLE ASC")

        if (cursor.moveToFirst()) {
            do {
                val song = Song(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    chords = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHORDS)),
                    lyrics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LYRICS)),
                    tuning = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUNING)),
                    capo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPO))
                )
                songsList.add(song)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return songsList
    }

    fun deleteSong(id : Int): Int {
        val db = writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    fun updateSong(song: Song): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, song.title)
            put(COLUMN_CHORDS, song.chords)
            put(COLUMN_LYRICS, song.lyrics)
            put(COLUMN_TUNING, song.tuning)
            put(COLUMN_CAPO, song.capo)
        }
        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(song.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun getSongById(id : Int): Song? {
        val db = writableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_ID = ?", arrayOf(id.toString()), null, null, null)
        var song : Song? = null
        if(cursor.moveToFirst()) {
            song = Song(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                chords = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHORDS)),
                lyrics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LYRICS)),
                tuning = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUNING)),
                capo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPO))
            )
        }
        cursor.close()
        db.close()
        return song
    }

}