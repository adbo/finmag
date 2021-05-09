package com.example.finmag

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri


class EntryContentProvider : ContentProvider() {
    companion object {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        const val AUTHORITY = "com.example.finmag"
        const val TABLE_NAME = "entry"
        const val ID_ENTRY_DATA = 1
        const val ID_ENTRY_DATA_ITEM = 2


    }

    init {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_ENTRY_DATA);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME +"/*", ID_ENTRY_DATA_ITEM);
    }


    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}