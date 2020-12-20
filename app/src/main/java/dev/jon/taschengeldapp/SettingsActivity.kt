package dev.jon.taschengeldapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.System.out
import kotlin.concurrent.timerTask


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(settings_toolbar)

        val db = Firebase.firestore

        backbutton_settings.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        logout_settings.setOnClickListener{
            logout()
        }
        textView7.setOnClickListener {
            export()

        }
        watch_tutorial.setOnClickListener{
            val intent = Intent(this, intro::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private fun logout(){
        Firebase.auth.signOut()
        val intent = Intent(this, Signup::class.java);
        startActivity(intent);
        datesChild.clear()
        infosChild.clear()
        transactionsizeChild.clear()
        balancesChilds.clear()
        namesChilds.clear()
        idsChilds.clear()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish();
    }
    private fun export(){
        //generate data
        val db = Firebase.firestore
        var data =""
        data+="info,transaction,date"
        var info:MutableList<String>
        var trans:MutableList<Double>
        var date:MutableList<String>


        try {

            db.collection("users").document(uidd).collection("childs")
                .get()
                .addOnSuccessListener {
                    for(document in it){

                        data+="\n"
                        val name = document.data["name"]
                        data+= "name:,$name"
                        info = document.data["infos"] as MutableList<String>
                        trans = document.data["transactions"] as MutableList<Double>
                        date = document.data["dates"]as MutableList<String>
                        if(info.size > 0){
                            for(da in 1..info.size){
                                data+="\n${date[da-1]},${info[da-1]},${trans[da-1]}"
                            }
                        }

                    }

                    try{
                        //saving the file into device
                        val out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                        out.write(data.toByteArray());
                        out.close();
                        val filelocation = File(filesDir, "data.csv");
                        val path = FileProvider.getUriForFile(this, "com.example.exportcsv.fileprovider", filelocation);
                        val fileIntent = Intent(Intent.ACTION_SEND);
                        fileIntent.setType("text/csv");
                        fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                        fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                        startActivity(Intent.createChooser(fileIntent, "Send mail"));
                    }
                    catch( e: Exception){
                        e.printStackTrace();
                    }

                }
                .addOnFailureListener{
                    Toast.makeText(this, "You have nothing to export!", Toast.LENGTH_LONG).show()
                }

        } catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        }

    }

}

