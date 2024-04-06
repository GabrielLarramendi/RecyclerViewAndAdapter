package dominando.android.recyclerviewcap.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dominando.android.recyclerviewcap.model.Message

class MessageAdapter(private val messages: List<Message>,
                     private val callback: (Message) -> Unit):
                     RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){

    class MessageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val textTitle:TextView = itemView.findViewById(dominando.android.recyclerviewcap.R.id.textTitle)
        val textText:TextView = itemView.findViewById(dominando.android.recyclerviewcap.R.id.textText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(dominando.android.recyclerviewcap.R.layout.item_message, parent, false)
        val viewHolder = MessageViewHolder(layoutView)

        viewHolder.itemView.setOnClickListener {
            val message = messages[viewHolder.absoluteAdapterPosition]
            callback(message)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val (title, text) = messages[position]
        holder.textTitle.text = title
        holder.textText.text = text
    }
}