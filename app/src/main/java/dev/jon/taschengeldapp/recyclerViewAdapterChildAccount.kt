package dev.jon.taschengeldapp

import android.content.Context
import android.graphics.Color
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChildAdapter(private val context: Context,
                  private val list: ArrayList<AccountChild>,
                  private val cellClickListener: CellClickListener) : RecyclerView.Adapter<ChildAdapter.ViewHoldernew>() {

    class ViewHoldernew(view: View): RecyclerView.ViewHolder(view) {
        val info: TextView = view.findViewById(R.id.childaccount_info)
        val amount: TextView = view.findViewById(R.id.childaccount_amount)
        val date: TextView = view.findViewById(R.id.childaccount_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldernew {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_child_account,parent, false)
        return ViewHoldernew(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun update(){
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHoldernew, position: Int) {
        val data = list[position]
        holder.info.text = data.info
        holder.amount.text = data.amount.toString()
        holder.date.text = data.date

        if(data.amount >= 0){
            holder.amount.setTextColor(Color.parseColor("#0080FF"))
        }else{
            holder.amount.setTextColor(Color.parseColor("#FF4D4D"))
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data,position)
        }
    }
}
interface CellClickListener {
    fun onCellClickListener(data: AccountChild,position: Int)
}