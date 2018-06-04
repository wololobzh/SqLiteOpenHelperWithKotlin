package fr.acos.sqliteopenhelperwithkotlin.dao

import android.content.ContentValues
import android.content.Context
import fr.acos.sqliteopenhelperwithkotlin.bo.Personne
import fr.acos.sqliteopenhelperwithkotlin.contracts.Contracts

class PersonneDao(context: Context)
{
    val dbHelper= BddHelper(context)

    fun insert(personne:Personne):Long?
    {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Contracts.Personnes.COLUMN_NAME_NOM, personne.nom)
            put(Contracts.Personnes.COLUMN_NAME_PRENOM, personne.prenom)
        }
        return db?.insert(Contracts.Personnes.TABLE_NAME, null, values)
    }

    fun get(id:Int):Personne?
    {
        var resultat:Personne?= null;
        val db = dbHelper.readableDatabase

        val projection = arrayOf(Contracts.Personnes.COLUMN_NAME_ID, Contracts.Personnes.COLUMN_NAME_NOM, Contracts.Personnes.COLUMN_NAME_PRENOM)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${Contracts.Personnes.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf("$id")


        val cursor = db.query(
                Contracts.Personnes.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        )

        with(cursor) {
            if (moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow(Contracts.Personnes.COLUMN_NAME_ID))
                val itemNom = getString(getColumnIndexOrThrow(Contracts.Personnes.COLUMN_NAME_NOM))
                val itemPrenom = getString(getColumnIndexOrThrow(Contracts.Personnes.COLUMN_NAME_PRENOM))
                resultat = Personne(itemId,itemNom,itemPrenom)
            }
        }
        return resultat;
    }

    fun update(item:Personne)
    {
        val db = dbHelper.writableDatabase
        // New value for one column
        val title = "MyNewTitle"
        val values = ContentValues().apply {
            put(Contracts.Personnes.COLUMN_NAME_ID, item.id)
            put(Contracts.Personnes.COLUMN_NAME_NOM, item.nom)
            put(Contracts.Personnes.COLUMN_NAME_PRENOM, item.prenom)
        }

        // Which row to update, based on the title
        val selection = "${Contracts.Personnes.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf("${item.id}")
        val count = db.update(
                Contracts.Personnes.TABLE_NAME,
                values,
                selection,
                selectionArgs)
    }

    fun delete(id:Int)
    {
        val db = dbHelper.writableDatabase
        // Define 'where' part of query.
        val selection = "${Contracts.Personnes.COLUMN_NAME_ID} = ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf("$id")
        // Issue SQL statement.
        db.delete(Contracts.Personnes.TABLE_NAME, selection, selectionArgs)
    }
}