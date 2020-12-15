package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_child_account_acitivity.*
import kotlinx.android.synthetic.main.activity_settings.*

class ChildAccountAcitivity : AppCompatActivity() {

    var datesChild = mutableListOf<String>();
    var infosChild = mutableListOf<String>();
    var transactionsizeChild = mutableListOf<Double>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_account_acitivity)
        setSupportActionBar(child_account_toolbar)

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val balance = intent.getDoubleExtra("balance",99999.0)

        supportActionBar?.title = "     $name' s account";
        backbutton_childaccount.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

    }
    override fun onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



    private fun loadData(){

        datesChild.add("12.42.21")
        datesChild.add("38.23.41")
        datesChild.add("22.10.32")
        infosChild.add("pocket money")
        infosChild.add("new clock")
        infosChild.add("pocket money")
        transactionsizeChild.add(25.0)
        transactionsizeChild.add(-29.5)
        transactionsizeChild.add(25.0)


        // TODO FIREBASE LOAD IN LISTS ETC



    }

    private fun updateDataOfChild(){
        var settingChild = "";
        var moneyChild = 0.0;
        var nameChild = "";

        // Create pop up, fill in the data, and let it change, save it

        // TODO UPDATE IN DB
    }
}