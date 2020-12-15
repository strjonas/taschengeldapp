package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_child_account_acitivity.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.add_payment_popup.*
import java.util.*

class ChildAccountAcitivity : AppCompatActivity(), CellClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_account_acitivity)
        setSupportActionBar(child_account_toolbar)

        loadData()

        val recyclerView: RecyclerView = findViewById(R.id.recViewChildAccount)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChildAdapter(this, fetchListnew(), this)

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val balance = intent.getDoubleExtra("balance",99999.0)

        balance_inchildactivity.text = "Balance: " + balance.toString()

        supportActionBar?.title = "     $name' s account";
        backbutton_childaccount.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        second.setOnClickListener{
            createDialog()
            floating_action_menu.collapse()
        }

    }
    fun createDialog(){
        val dibu = AlertDialog.Builder(this)
        val popup = layoutInflater.inflate(R.layout.add_payment_popup, null)
        dibu.setView(popup)
        val dialog = dibu.create()
        dialog.show()
        val cancelbutton = popup.findViewById<Button>(R.id.button_cancel_popup)
        val addbutton = popup.findViewById<Button>(R.id.button_add_popup)
        val edittext = popup.findViewById<EditText>(R.id.editTextTextPersonName2)
        val editnum = popup.findViewById<EditText>(R.id.editTextNumberDecimal)
        cancelbutton.setOnClickListener{
            dialog.dismiss()
        }
        addbutton.setOnClickListener{
            datesChild.add(0,getDate())
            infosChild.add(0,edittext.text.toString())
            transactionsizeChild.add(0,editnum.text.toString().toDouble())
            dialog.dismiss()
            ChildAdapter(this,fetchListnew(),this).update()
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

        }
    }
    override fun onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    override fun onCellClickListener(data: AccountChild,position: Int) {

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
    fun fetchListnew(): ArrayList<AccountChild> {
        val list = arrayListOf<AccountChild>()
        for (i in 1..infosChild.size) {
            val child = AccountChild(infosChild[i-1], datesChild[i-1], transactionsizeChild[i-1])
            list.add(child)
        }
        return list
    }


    private fun loadData(){

        // TODO FIREBASE LOAD IN LISTS ETC                                              ^
        // Make sure to always place the new one in the first spot of the list like so: |
    }

    private fun updateDataOfChild(){
        var settingChild = "";
        var moneyChild = 0.0;
        var nameChild = "";

        // Create pop up, fill in the data, and let it change, save it

        // TODO UPDATE IN DB
    }
    fun getDate(): String{
        val calendar= Calendar.getInstance(TimeZone.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return "$day.$month.$year"
    }
}