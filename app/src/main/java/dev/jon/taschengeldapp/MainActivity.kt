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

class MainActivity : AppCompatActivity(), CellClickListenerNew {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Overview"

        val recyclerView: RecyclerView = findViewById(R.id.recViewMain)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainAdapter(this, fetchList(), this)

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener { view ->

            val intent = Intent(this, addChild::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_up, R.anim.slide_up_out)

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

        startActivity(intent)

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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