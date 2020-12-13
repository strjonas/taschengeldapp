package dev.jon.taschengeldapp

import android.content.Context
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val context: Context,
                  private val list: ArrayList<Child>,
                  private val cellClickListener: CellClickListenerNew) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val balance: TextView = view.findViewById(R.id.child_balance_text)
        val name: TextView = view.findViewById(R.id.child_name_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_main,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.name.text = data.nameChild
        holder.balance.text = data.balanceChild.toString();

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListenerNew(data,position)
        }
    }
}
interface CellClickListenerNew {
    fun onCellClickListenerNew(data: Child,position: Int)
}