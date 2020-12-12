package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.lang.Thread.sleep

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();


        // if data is loaded -> goto main Screen
        //val intent = Intent(this, MainActivity::class.java);
        //startActivity(intent);
        //finish();
    }
}