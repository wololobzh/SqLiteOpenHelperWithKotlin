package fr.acos.sqliteopenhelperwithkotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.acos.sqliteopenhelperwithkotlin.bo.Personne
import fr.acos.sqliteopenhelperwithkotlin.dao.PersonneDao

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = PersonneDao(this)

        val personne1 = Personne(null,"Baudouin","Julie")
        val personne2 = Personne(null,"Gleonnec","Guenole")

        dao.insert(personne1)
        dao.insert(personne2)

        val personne3 = dao.get(1)

        Log.i("XXX",personne3.toString())

        val personne4 = Personne(1,"ddd","fff")

        dao.update(personne4)

        val personne5 = dao.get(1)

        Log.i("XXX",personne5.toString())

        dao.delete(1)

        val personne6 = dao.get(1)

        Log.i("XXX",personne6.toString())
    }
}
