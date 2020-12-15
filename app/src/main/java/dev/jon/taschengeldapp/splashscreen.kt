package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.lang.Thread.sleep

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide();

        loadData()
        updateBalance()

        // if data is loaded -> goto main Screen
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
        finish();
    }

    private fun loadData(){

        // TODO (database)
        namesChilds.add("alex")
        namesChilds.add("larissa")
        idsChilds.add("1234")
        idsChilds.add("1235")
        balancesChilds.add(23.00)
        balancesChilds.add(15.00)
    }
    private fun updateBalance(){
        for (id in idsChilds){
            //check the id, check last payment variable, if its not up to date: ->
            updateChildsBalance()
        }
    }
    private fun updateChildsBalance(){
        //calculate back and give pending money
        //last payment = today
    }
}