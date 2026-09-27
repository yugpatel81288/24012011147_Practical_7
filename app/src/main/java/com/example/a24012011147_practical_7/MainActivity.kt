package com.example.a24012011147_practical_7
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar // <-- Make sure this import is present
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val personList = ArrayList<Person>()
    lateinit var personsRecycleAdapter: PersonAdapter
    lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set toolbar as action bar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DatabaseHelper(applicationContext)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView1)
        personsRecycleAdapter = PersonAdapter(this, personList)
        recyclerView.adapter = personsRecycleAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            getPersonDetailsFromSQLiteDb()
        }
    }

    private fun getPersonDetailsFromSQLiteDb() {
        val personListFromDb = db.allPersons
        personList.clear()
        personList.addAll(personListFromDb)
        personsRecycleAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Fetched from SQLite DB", Toast.LENGTH_SHORT).show()
    }

    private fun networkDb() {
        val JSON_URL = "https://api.json-generator.com/templates/k-ysq0j-b-P_/data"
        val API_TOKEN = "b04r3k1044q3d31g0l1j3yv5s1n5f2vyg47n4a0g"

        CoroutineScope(Dispatchers.IO).launch {
            val hr = HttpRequest()
            val sJson = hr.makeServiceCall(JSON_URL, API_TOKEN)

            withContext(Dispatchers.Main) {
                // !! ERROR FIXED: Added null check to prevent sJson!! crash
                if (sJson != null) {
                    getPersonDetailsFromJson(sJson)
                } else {
                    Toast.makeText(applicationContext, "Network Error: Could not fetch data", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getPersonDetailsFromJson(sJson: String) {
        val size = personList.size
        personList.clear()
        personsRecycleAdapter.notifyItemRangeRemoved(0, size)

        try {
            val jsonArray = JSONArray(sJson)
            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray[i] as JSONObject
                val person = Person(jsonObject) // This line can still crash, see Person.kt fix

                personList.add(person)

                try {
                    if (db.getPerson(person.id) != null)
                        db.updatePerson(person)
                    else
                        db.insertPerson(person)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            personsRecycleAdapter.notifyItemRangeInserted(0, personList.size)

        } catch (ee: JSONException) {
            ee.printStackTrace()
        }

        Toast.makeText(this, "Fetch details from JSON", Toast.LENGTH_SHORT).show()
    }

    // !! ERROR FIXED: Added the missing deletePerson function
    fun deletePerson(position: Int) {
        try {
            val person = personList[position]
            db.deletePerson(person.id)
            personList.removeAt(position)
            personsRecycleAdapter.notifyItemRemoved(position)
            personsRecycleAdapter.notifyItemRangeChanged(position, personList.size)
            Toast.makeText(this, "${person.name} deleted", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error deleting person", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_sqlitedb -> {
                getPersonDetailsFromSQLiteDb()
                true
            }

            R.id.action_nwdb -> {
                networkDb()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}