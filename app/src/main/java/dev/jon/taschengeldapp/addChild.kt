package dev.jon.taschengeldapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_child.*
import kotlinx.android.synthetic.main.activity_settings.*


class addChild : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)
        setSupportActionBar(addchild_toolbar)
        val nums = initialisePicker()
        auth = Firebase.auth

        button_addchild.setOnClickListener{

            getData(nums)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        backbutton_addchild.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
    override fun onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private fun getData(nums: Array<String>){
        if(!checkdata()){
            return
        }
        val name = editTextTextPersonName.text.toString()
        val setting = if(checkbox_monthly.isChecked){
            "mon"
        }else{
            "wek"
        }
        val money = nums[spinner2.value]
        updateData(name,setting,money)

    }
    private fun updateData(name:String, setting:String, money:String){
        val db =Firebase.firestore

        val id = System.currentTimeMillis().toString()
        balancesChilds.add(money.toDouble())
        idsChilds.add(id)
        namesChilds.add(name)

        childs.add(id)

        val date = ChildAccountAcitivity().getDate()

        val child = hashMapOf(
                "balance" to 0.0,
                "moneyper" to money.toDouble(),
                "id" to id,
                "setting" to setting,
                "name" to name,
                "lastpayment" to date,
                "dates" to datesChild,
                "infos" to infosChild,
                "transactions" to transactionsizeChild

        )
        db.collection("users").document(uidd).collection("childs").document(id)
                .set(child)
                .addOnSuccessListener {
                    db.collection("users").document(uidd)
                            .update("childs",childs)
                            .addOnSuccessListener {
                                MainAdapter(MainActivity(), MainActivity().fetchList(), MainActivity()).update()
                                MainActivity().finish()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent);
                                overridePendingTransition(0,0)
                                finish()

                            }
                }

    }
    private fun checkdata(): Boolean{
        if(editTextTextPersonName.text.isEmpty()){
            Snackbar.make(constraintlayout_add_child,"Please provide a name!",Snackbar.LENGTH_LONG).show()
            return false
        }
        if(checkbox_monthly.isChecked and checkbox_weekly.isChecked){
            Snackbar.make(constraintlayout_add_child,"You can only select one payment option!",Snackbar.LENGTH_LONG).show()
            return false
        }
        if(!checkbox_weekly.isChecked and !checkbox_monthly.isChecked){
            Snackbar.make(constraintlayout_add_child,"Please provide a payment option!",Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun initialisePicker(): Array<String>{
        val nums: Array<String> = arrayOf(
            "0.5","1.0",  "1.5","2.0",  "2.5", "3.0",  "3.5", "4.0", "4.5", "5.0",  "5.5",
            "6.0",  "6.5", "7.0",  "7.5", "8.0",  "8.5","9.0",  "9.5",   "10.0",   "10.5",
            "11.0", "11.5","12.0","12.5","13.0","13.5", "14.0","14.5","15.0","15.5","16.0",
            "16.5","17.0","17.5","18.0","18.5","19.0","19.5","20.0","20.5","21.0","21.5","22.0",
            "22.5","23.0","23.5","24.0","24.5","25.0","25.5","26.0","26.5","27.0","27.5","28.0",
            "28.5","29.0","29.5","30.0","30.5","31.0","31.5","32.0","32.5","33.0","33.5","34.0",
            "34.5","35.0","35.5","36.0","36.5","37.0","37.5","38.0","38.5","39.0","39.5","40.0",
            "40.5","41.0","41.5","42.0","42.5","43.0","43.5","44.0","44.5","45.0","45.5","46.0",
            "46.5","47.0","47.5","48.0","48.5","49.0","49.5","50.0"
        )
        spinner2.maxValue = nums.size - 1
        spinner2.minValue = 0
        spinner2.wrapSelectorWheel = false
        spinner2.displayedValues = nums
        spinner2.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        return nums
    }

}