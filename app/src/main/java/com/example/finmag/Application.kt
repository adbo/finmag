package com.example.finmag

import android.app.Application

class EntryApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.entryDao()) }
}