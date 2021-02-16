package ir.burooj.basij.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.burooj.basij.R
import ir.burooj.basij.apiClass.MessageModel

class MessageAdapter(private val myDataSet: List<MessageModel>) :
        RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val textName = itemView.findViewById<TextView>(R.id.message)
        fun bind(position: Int, members: MessageModel) {
            textName.text = members.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MessageAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
        return MyViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, myDataSet[position])
    }

    override fun getItemCount() = myDataSet.size
}

