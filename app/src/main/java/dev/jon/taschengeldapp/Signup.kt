package dev.jon.taschengeldapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*


class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        auth = Firebase.auth

        var email:String;
        var password:String;
        var name = "default";
        var currency:String;

        spinner()

        signup_button.setOnClickListener{
            email = signup_email.text.toString()
            password = signup_password.text.toString()
            name = signup_name.text.toString()
            currency = spinner.selectedItem.toString()
            currencyUser = currency
            val ag = login(email,password,name,currency)
            if(ag){
                return@setOnClickListener
            }
        }
        login_change_button.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()

        }
    }
    private fun login(useremail:String, userpassword:String, name:String, currency: String): Boolean{
        val db =Firebase.firestore
        if(useremail.length < 6){
            signup_email.error = "Email is required!"
            return true
        }
        if("@" in useremail && "." in useremail){
        }
        else{
            signup_email.error = "Not a valid email!"
            return true
        }
        if(userpassword.length < 6){
            signup_password.error = "Password is to short!"
            return true
        }

        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(useremail,userpassword)
            .addOnCompleteListener{
                if(it.isSuccessful){

                    val userId = auth.currentUser?.uid
                    uidd = userId!!
                    val user = hashMapOf(
                        "name" to name,
                        "email" to useremail,
                        "currency" to currency,
                            "childs" to childs,
                        "userid" to userId
                    )
                    db.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finish()

                        }
                        .addOnFailureListener { e ->
                            progressBar.visibility = View.INVISIBLE
                            Snackbar.make(signup_constraintLayout,"Something went wrong, sorry!", Snackbar.LENGTH_LONG).show()
                            return@addOnFailureListener
                        }
                }
                else{
                    progressBar.visibility = View.INVISIBLE
                    Snackbar.make(signup_constraintLayout,"Something went wrong, sorry!", Snackbar.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }
            }
        return true
    }
    fun spinner(){
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.spinner_currency,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

    }

}