package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splashscreen.*
import java.lang.Exception
import java.lang.Thread.sleep
import java.util.*

class splashscreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide();
        auth = Firebase.auth
        balancesChilds.clear()

        datesChild.clear()
        infosChild.clear()
        transactionsizeChild.clear()
        balancesChilds.clear()
        namesChilds.clear()
        idsChilds.clear()

        val isAuth = checkauth()
        if (isAuth) {
            loadData()


        } else {
            val intent = Intent(this, Signup::class.java);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish();
        }

        Handler().postDelayed(
            {
                Snackbar.make(splash_lay, "Seems like we are having trouble connecting you to our Database!\n Are you connected to the Internet?",10000).show()

            },5000
        )


    }

    private fun loadData() {
        val db = Firebase.firestore
        val id = auth.currentUser?.uid!!
        uidd = id
        db.collection("users").document(id).get()
                .addOnSuccessListener {
                    currencyUser = it.get("currency").toString()
                    nameUser = it.get("name").toString()
                    childs = it.get("childs") as MutableList<String>
                    if(childs.size > 0){
                        db.collection("users").document(id).collection("childs")
                                .get()
                                .addOnSuccessListener {
                                    for (document in it) {
                                        namesChilds.add(0,document.get("name").toString())
                                        idsChilds.add(0,document.get("id").toString())

                                    }
                                    updateBalance()
                                }
                    }else{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish();
                    }

                }

    }

    private fun updateBalance() {
        for (id in idsChilds) {
            try {
                updateChildsBalance(id)

            } catch (e: Exception) {
                Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun updateChildsBalance(id: String) {

        val db = Firebase.firestore
        db.collection("users").document(uidd).collection("childs").document(id)
                .get()
                .addOnSuccessListener {
                    val monthly = it.get("setting") as String
                    val pocketMoney = it.get("moneyper") as Double
                    val lastpayment = it.get("lastpayment") as String

                    transactionsizeChild = it.get("transactions") as MutableList<Double>
                    infosChild = it.get("infos") as MutableList<String>
                    datesChild = it.get("dates") as MutableList<String>

                    val info: String
                    val date: String
                    val transaction: Double


                    if (monthly == "mon") {

                        val dateArray = lastpayment.split(".")
                        val lastmonth = dateArray[1]
                        val lastyear = dateArray[2]
                        val calender = Calendar.getInstance(TimeZone.getDefault())
                        val day = calender.get(Calendar.DAY_OF_YEAR)
                        val month = calender.get(Calendar.MONTH) + 1
                        val year = calender.get(Calendar.YEAR)
                        val dayOfMonth = calender.get(Calendar.DAY_OF_MONTH)
                        val paySum = (lastmonth.toInt() - month + (lastyear.toInt() - year) * 12) * pocketMoney

                        info = resources.getString(R.string.money_given)
                        date = "$dayOfMonth.$month.$year"
                        transaction = paySum * -1


                    } else {

                        val dateArray = lastpayment.split(".")
                        val lastday = dateArray[0]
                        val lastyear = dateArray[2]
                        val calender = Calendar.getInstance(TimeZone.getDefault())
                        val day = calender.get(Calendar.DAY_OF_YEAR)
                        val month = calender.get(Calendar.MONTH) + 1
                        val year = calender.get(Calendar.YEAR)
                        val dayOfMonth = calender.get(Calendar.DAY_OF_MONTH)
                        val paySum = (lastday.toInt() - day + (lastyear.toInt() - year) * 365) * pocketMoney

                        info = resources.getString(R.string.money_given)
                        date = "$dayOfMonth.$month.$year"
                        transaction = paySum * -1
                    }

                    if (transaction != -0.0 || transaction != 0.0 || transaction != 0.0) {
                        datesChild.add(0, date)
                        infosChild.add(0, info)
                        transactionsizeChild.add(0, transaction)
                        val conn = db.collection("users").document(uidd).collection("childs").document(id)

                        conn.update("dates", datesChild)
                        conn.update("infos", infosChild)
                        conn.update("transactions", transactionsizeChild)

                        conn.update("lastpayment", getPayDate())
                        var balance = 0.0
                        for (i in transactionsizeChild) {
                            balance += i
                        }
                        transactionsizeChild.clear()
                        balancesChilds.add(0, balance)
                        conn.update("balance", balance)

                    } else {
                        val conn = db.collection("users").document(uidd).collection("childs").document(id)
                        var balance = 0.0
                        for (i in transactionsizeChild) {
                            balance += i
                        }
                        transactionsizeChild.clear()
                        balancesChilds.add(0, balance)
                        conn.update("balance", balance)
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish();

                }
    }

    private fun checkauth(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    fun getPayDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_YEAR)

        return "$day.$month.$year"
    }
}