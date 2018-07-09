package fr.acos.sqliteopenhelperwithkotlin.dao

import android.content.ContentValues
import android.content.Context
import fr.acos.sqliteopenhelperwithkotlin.bo.Personne
import fr.acos.sqliteopenhelperwithkotlin.contracts.Contracts

class PersonneDao(context: Context)
{
    //Récupération d'un objet BddHelper permettant de récupérer des connexion à la base de données
    private val dbHelper= BddHelper(context)

    /**
     * Fonction permettant d'enregistrer une personne en base de données
     * Une requête INSERT sera envoyée à la bdd.
     */
    fun insert(personne:Personne):Long?
    {
        //Récupération d'un objet représentant une connexion à la base de données.
        //Si la base de données n'existe pas alors elle est créée.
        val db = dbHelper.writableDatabase
        //Objet permettant d'enregister des données sous forme de clé/valeur
        //La clé est le nom d'une colonne
        //La valeur est la valeur à insérer dans la colonne de l'enregistrement.
        val values = ContentValues().apply {
            put(Contracts.Personnes.COLUMN_NAME_NOM, personne.nom)
            put(Contracts.Personnes.COLUMN_NAME_PRENOM, personne.prenom)
        }
        //Appel de la fonction insert qui enregistre les données en base.
        return db?.insert(Contracts.Personnes.TABLE_NAME, null, values)
    }

    /**
     * Fonction permettant de récupérer un objet de type Personne grâce à son identifiant
     * Une requête SELECT sera envoyée à la bdd.
     */
    fun get(id:Int):Personne?
    {
        //Objet de type Personne permettant d'enregistrer les informations à retourner
        var resultat:Personne?= null;
        //Récupération d'un objet représentant une connexion en lecture sur la base de données.
        //Si la base de données n'existe pas alors elle est créée.
        val db = dbHelper.readableDatabase

        //Définition de la projection de la requête SELECT
        val projection = arrayOf(Contracts.Personnes.COLUMN_NAME_ID, Contracts.Personnes.COLUMN_NAME_NOM, Contracts.Personnes.COLUMN_NAME_PRENOM)

        // Définition du WHERE de la reqûete SELECT
        //Le WHERE contient un paramètre représenté par le "?"
        val selection = "${Contracts.Personnes.COLUMN_NAME_ID} = ?"
        //Valeur qui sera fournit au paramètre de la restriction WHERE
        val selectionArgs = arrayOf("$id")

        //Appel de la fonction query qui exécute le SELECT et retourne le resultat de la requête sous forme de cursor
        val cursor = db.query(
                Contracts.Personnes.TABLE_NAME,   // Definition de la table dans laquel a lieu la recherche (Définition du FROM)
                projection,             // Définition du SELECT
                selection,              // Définition du WHERE
                selectionArgs,          // Valeur pour la clause WHERE
                null,           // Pas de GROUP BY dans cette requête
                null,            // Pas de having dans cette requête
                null             // Pas de tri dans cette requête
        )

        //Travail sur le curseur
        with(cursor) {
            //fonction moveToNext de l'objet cursor
            if (moveToNext()) {
                //Récupération de l'identifiant dans le resultat de la requête
                val itemId = getInt(getColumnIndexOrThrow(Contracts.Personnes.COLUMN_NAME_ID))
                //Récupération du nom dans le resultat de la requête
                val itemNom = getString(getColumnIndexOrThrow(Contracts.Personnes.COLUMN_NAME_NOM))
                //Récupération du prénom dans le resultat de la requête
                val itemPrenom = getString(getColumnIndexOrThrow(Contracts.Personnes.COLUMN_NAME_PRENOM))
                //Construction de l'objet de type Personne qui sera la valeur retourné.
                resultat = Personne(itemId,itemNom,itemPrenom)
            }
        }
        //Retourne le résultat
        return resultat;
    }

    /**
     * Fonction permettant de mettre à jour un enregistrement dans la table personnes
     * Une requête UPDATE sera envoyée à la bdd.
     */
    fun update(item:Personne)
    {
        //Récupération d'un objet représentant une connexion en écriture sur la base de données.
        //Si la base de données n'existe pas alors elle est créée.
        val db = dbHelper.writableDatabase
        //Objet permettant d'enregister des données sous forme de clé/valeur
        //La clé est le nom d'une colonne
        //La valeur est la valeur à mettre à jour dans la colonne de l'enregistrement.
        val values = ContentValues().apply {
            put(Contracts.Personnes.COLUMN_NAME_ID, item.id)
            put(Contracts.Personnes.COLUMN_NAME_NOM, item.nom)
            put(Contracts.Personnes.COLUMN_NAME_PRENOM, item.prenom)
        }

        // Définition du WHERE de la reqûete UPDATE
        //Le WHERE contient un paramètre représenté par le "?"
        val selection = "${Contracts.Personnes.COLUMN_NAME_ID} = ?"
        //Valeur qui sera fournit au paramètre de la restriction WHERE
        val selectionArgs = arrayOf("${item.id}")
        //Appel de la fonction query qui exécute la requête UPDATE et retourne le nombre de lignes mises à jour
        val count = db.update(
                Contracts.Personnes.TABLE_NAME,//Nom de la table à mettre à jour
                values, //Valeurs à mettre à jour
                selection, //Définition du WHERE
                selectionArgs) //Valeurs à mettre dans le WHERE
    }

    /**
     * Fonction permettant de supprimer un enregistrement dans la table personnes
     * Une requête DELETE sera envoyée à la bdd.
     */
    fun delete(id:Int)
    {
        //Récupération d'un objet représentant une connexion en écriture sur la base de données.
        //Si la base de données n'existe pas alors elle est créée.
        val db = dbHelper.writableDatabase
        // Définition du WHERE de la reqûete DELETE
        //Le WHERE contient un paramètre représenté par le "?"
        val selection = "${Contracts.Personnes.COLUMN_NAME_ID} = ?"
        //Valeur qui sera fournit au paramètre de la restriction WHERE
        val selectionArgs = arrayOf("$id")
        //Appel de la fonction query qui exécute la requête DELETE
        db.delete(Contracts.Personnes.TABLE_NAME, selection, selectionArgs)
    }
}