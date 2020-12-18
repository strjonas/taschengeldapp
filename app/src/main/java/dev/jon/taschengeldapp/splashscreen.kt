package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Thread.sleep

class splashscreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide();
        auth = Firebase.auth

        val isAuth = checkauth()
        if(isAuth){
            loadData()
            updateBalance()

        }else{
            val intent = Intent(this, Signup::class.java);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish();
        }


    }


    private fun loadData(){
        val db = Firebase.firestore
        val id = auth.currentUser?.uid!!
        uidd = id

        db.collection("users").document(id).get()
                .addOnSuccessListener {
                    currencyUser = it.get("currency").toString()
                    nameUser = it.get("name").toString()
                    childs = it.get("childs") as MutableList<String>
                    db.collection("users").document(id).collection("childs")
                            .get()
                            .addOnSuccessListener {
                                for(document in it){
                                    namesChilds.add(document.get("name").toString())
                                    idsChilds.add(document.get("id").toString())
                                    balancesChilds.add(document.get("balance").toString().toDouble())
                                }
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                finish();
                            }
                }

        // TODO (make following in childaccountactivity and get them from database
        /*datesChild.add(0,"12.42.21")
        datesChild.add(0,"38.23.41")
        datesChild.add(0,"22.10.32")
        infosChild.add(0,"pocket money")
        infosChild.add(0,"new clock")
        infosChild.add(0,"pocket money")
        transactionsizeChild.add(0,25.0)
        transactionsizeChild.add(0,-29.5)
        transactionsizeChild.add(0,25.0)*/

        // TODO when childs are loaded and there is no child, display tutorial, background add child blabla

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
    private fun checkauth(): Boolean{
        val currentUser = auth.currentUser
        return currentUser != null
    }
}