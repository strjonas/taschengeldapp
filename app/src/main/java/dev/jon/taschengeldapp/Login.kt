package dev.jon.taschengeldapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        var email:String;
        var password:String;

        // TODO forgot password

        login_button.setOnClickListener{

            email = login_email.text.toString()
            password = login_password.text.toString()

            login(email,password)
        }

        signup_change_button.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish()
        }

    }

    fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    private fun login(useremail:String, userpassword:String): Boolean{
        val db =Firebase.firestore
        if(useremail.length < 6){
            login_email.error = "Email is required!"
            return true
        }
        if("@" in useremail){
        }
        else{
            login_email.error = "Not a valid email!"
            return true
        }
        if(userpassword.length < 6){
            login_password.error = "Password is to short!"
            return true
        }
        auth.signInWithEmailAndPassword(useremail,userpassword)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    val userId = auth.currentUser?.uid
                    uidd = userId!!
                    db.collection("users").document(userId)
                        .get()
                        .addOnSuccessListener {
                            currencyUser = it.getString("currency")!!
                            nameUser = it.getString("name")!!

                            // to load the child data we go to the splashscreen activity
                            val intent = Intent(this, splashscreen::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                            finish()

                        }
                        .addOnFailureListener { e ->
                            Snackbar.make(login_layout,"Wrong username or password!", Snackbar.LENGTH_LONG).show()
                            return@addOnFailureListener
                        }
                }
                else{
                    Snackbar.make(login_layout,"Wrong username or password!", Snackbar.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }
            }
            return true


    }
}