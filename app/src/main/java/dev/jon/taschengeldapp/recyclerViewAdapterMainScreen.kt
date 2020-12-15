package dev.jon.taschengeldapp

import android.content.Context
import android.graphics.Color
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainAdapter(private val context: Context,
                  private val list: ArrayList<Child>,
                  private val cellClickListener: CellClickListenerNew) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val balance: TextView = view.findViewById(R.id.child_balance_text)
        val name: TextView = view.findViewById(R.id.child_name_text)
        val pay: Button = view.findViewById(R.id.pay_pocketmoney_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_main,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun update(){
        list.clear()
        for (i in 1..balancesChilds.size) {
            val child = Child(idsChilds[i-1], namesChilds[i-1],balancesChilds[i-1])
            list.add(child)
        }
        notifyDataSetChanged()
    }
    private fun pay(id:String, position: Int){
        // when database is connected where id == id, and take money from there and update the balance
        val money = 25.0
        balancesChilds[position] -= money
        update()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.name.text = data.nameChild
        holder.balance.text = data.balanceChild.toString();

        if(data.balanceChild >= 0){
            holder.balance.setTextColor(Color.parseColor("#0080FF"))
        }else{
            holder.balance.setTextColor(Color.parseColor("#FF4D4D"))
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListenerNew(data,position)
        }
        holder.pay.setOnClickListener{
            //Snackbar.make(holder.itemView,"${data.idChild}",Snackbar.LENGTH_LONG).show()
            pay(data.idChild, position)
            return@setOnClickListener
        }
    }
}
interface CellClickListenerNew {
    fun onCellClickListenerNew(data: Child,position: Int)
}

