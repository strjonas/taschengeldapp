package dev.jon.taschengeldapp

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.AccessController
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.schedule


class MainAdapter(private val context: Context,
                  private val list: ArrayList<Child>,
                  private val cellClickListener: CellClickListenerNew) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val balance: TextView = view.findViewById(R.id.child_balance_text)
        val name: TextView = view.findViewById(R.id.child_name_text)
        val pay: Button = view.findViewById(R.id.pay_pocketmoney_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun update(){
        list.clear()
        for (i in 1..balancesChilds.size) {
            val child = Child(idsChilds[i - 1], namesChilds[i - 1], balancesChilds[i - 1])
            list.add(child)
        }
        notifyDataSetChanged()
    }

    private fun pay(id: String, position: Int,holder: ViewHolder){
        var infos:MutableList<String>
        var dates:MutableList<String>
        var transactions:MutableList<Double>
        var money:Double
        val db = Firebase.firestore
        val conn =db.collection("users").document(uidd).collection("childs").document(id)
        conn.get()
                .addOnSuccessListener {
                    infos = it.get("infos") as MutableList<String>
                    dates = it.get("dates") as MutableList<String>
                    transactions = it.get("transactions") as MutableList<Double>

                    money = it.get("moneyper").toString().toDouble()

                    balancesChilds[position] -= money

                    infos.add(0, holder.itemView.resources.getString(R.string.money_given))
                    dates.add(0, ChildAccountAcitivity().getDate())
                    transactions.add(0, -money)

                    conn.update("infos", infos)
                    conn.update("transactions", transactions)
                    conn.update("dates", dates)

                    var balance = 0.0
                    for(i in transactions){
                        balance += i
                    }
                    conn.update("balance", balance)

                    update()
                }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.name.text = data.nameChild
        holder.balance.text = data.balanceChild.toString()  + " " + currencyUser

        val mode = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {holder.name.setTextColor(Color.BLACK)}
            Configuration.UI_MODE_NIGHT_NO -> {}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {holder.name.setTextColor(Color.BLACK)}
        }
        holder.pay.isClickable = true

        if(data.balanceChild >= 0){
            holder.balance.setTextColor(Color.parseColor("#0080FF"))
        }else{
            holder.balance.setTextColor(Color.parseColor("#FF4D4D"))
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListenerNew(data, position)
        }
        holder.pay.setOnClickListener{
            holder.pay.isEnabled = false
            pay(data.idChild, position, holder)
            holder.pay.isEnabled = true
            return@setOnClickListener

        }
    }
}

interface CellClickListenerNew {
    fun onCellClickListenerNew(data: Child, position: Int)
}

