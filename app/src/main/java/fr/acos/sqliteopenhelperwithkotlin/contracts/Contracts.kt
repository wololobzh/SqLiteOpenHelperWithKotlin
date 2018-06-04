package fr.acos.sqliteopenhelperwithkotlin.contracts

import android.provider.BaseColumns

object Contracts {

    object Personnes : BaseColumns {
        const val TABLE_NAME = "personnes"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NOM = "nom"
        const val COLUMN_NAME_PRENOM = "prenom"

        const val SQL_CREATE_TABLE =
                "CREATE TABLE ${TABLE_NAME} (" +
                        "${COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${COLUMN_NAME_NOM} TEXT," +
                        "${COLUMN_NAME_PRENOM} TEXT)"

        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${Personnes.TABLE_NAME}"
    }
}