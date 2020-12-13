package dev.jon.taschengeldapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_child_account_acitivity.*
import kotlinx.android.synthetic.main.activity_settings.*

class ChildAccountAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_account_acitivity)
        setSupportActionBar(child_account_toolbar)

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val balance = intent.getDoubleExtra("balance",99999.0)
        val position = intent.getIntExtra("position",99999)

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
}