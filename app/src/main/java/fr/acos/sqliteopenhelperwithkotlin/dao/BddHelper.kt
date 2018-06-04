package fr.acos.sqliteopenhelperwithkotlin.dao

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import fr.acos.sqliteopenhelperwithkotlin.contracts.Contracts
import android.content.Context

class BddHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Contracts.Personnes.SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(Contracts.Personnes.SQL_DELETE_TABLE)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "BddExemple.db"
    }
}