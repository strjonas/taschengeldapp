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
import java.util.*

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

    }
    private fun updateBalance(){
        for (id in idsChilds){
            //check the id, check last payment variable, if its not up to date: ->
            updateChildsBalance()
        }
    }
    private fun updateChildsBalance(){
        val monthly = true
        val pocketMoney = 1
        if(monthly){
            var lastpayment = "353/11/2019"
            val dateArray= lastpayment.split("/")
            val lastmonth = dateArray[1]
            val lastyear = dateArray[2]
            val calender = Calendar.getInstance(TimeZone.getDefault())
            val day= calender.get(Calendar.DAY_OF_YEAR)
            val month = calender.get(Calendar.MONTH)+1
            val year = calender.get(Calendar.YEAR)
            val dayOfMonth = calender.get(Calendar.DAY_OF_MONTH)
            val paySum = (lastmonth.toInt() - month + (lastyear.toInt()  - year)*12 )* pocketMoney

            val info = "Taschengeld Nachzahlung: " + paySum * -1 + "$, " + "from last payment till today"
            val date = "$dayOfMonth.$month.$year"
            val transaction = paySum*-1


        }else{
            var lastpayment = "323/11/2020"
            val dateArray= lastpayment.split("/")
            val lastday = dateArray[0]
            val lastyear = dateArray[2]
            val calender = Calendar.getInstance(TimeZone.getDefault())
            val day= calender.get(Calendar.DAY_OF_YEAR)
            val month = calender.get(Calendar.MONTH)+1
            val year = calender.get(Calendar.YEAR)
            val dayOfMonth = calender.get(Calendar.DAY_OF_MONTH)
            val paySum = (lastday.toInt() - day + (lastyear.toInt()  - year)*365 )* pocketMoney

            val info = "Taschengeld Nachzahlung: " + paySum * -1 + "$, " + "from last payment till today"
            val date = "$dayOfMonth.$month.$year"
            val transaction = paySum*-1

        }
    }
    private fun checkauth(): Boolean{
        val currentUser = auth.currentUser
        return currentUser != null
    }
}