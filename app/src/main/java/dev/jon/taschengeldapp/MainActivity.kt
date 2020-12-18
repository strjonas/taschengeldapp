 package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CellClickListenerNew {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Overview"

        val recyclerView: RecyclerView = findViewById(R.id.recViewMain)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainAdapter(this, fetchList(), this)

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener { view ->

            val intent = Intent(this, addChild::class.java)
            intent.putExtra("id","null")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.settings -> {
            val intento = Intent(this, SettingsActivity::class.java)
            startActivity(intento)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCellClickListenerNew(data: Child,position: Int) {
        val intent = Intent(this, ChildAccountAcitivity::class.java)
        intent.putExtra("position",position)
        intent.putExtra("balance",data.balanceChild)
        intent.putExtra("id",data.idChild)
        intent.putExtra("name",data.nameChild)

       loadChildLists(data.idChild, intent)
    }
    private fun loadChildLists(id:String, intent: Intent){
        val db = Firebase.firestore

        db.collection("users").document(uidd).collection("childs").document(id)
                .get()
                .addOnSuccessListener {
                    datesChild = it.get("dates") as MutableList<String>
                    infosChild = it.get("infos") as MutableList<String>
                    transactionsizeChild = it.get("transactions") as MutableList<Double>
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
                .addOnFailureListener{
                    Snackbar.make(add_child,"Couldn't fetch data", Snackbar.LENGTH_LONG).show()
                    return@addOnFailureListener
                }

    }
    fun fetchList(): ArrayList<Child> {
        val list = arrayListOf<Child>()
        for (i in 1..balancesChilds.size) {
            val child = Child(idsChilds[i-1], namesChilds[i-1],balancesChilds[i-1])
            list.add(child)
        }

        return list
    }


}