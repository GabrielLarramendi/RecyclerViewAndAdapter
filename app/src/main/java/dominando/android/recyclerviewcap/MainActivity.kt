package dominando.android.recyclerviewcap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import dominando.android.recyclerviewcap.adapters.MessageAdapter
import dominando.android.recyclerviewcap.model.Message

class MainActivity : AppCompatActivity() {

    private var messages = mutableListOf<dominando.android.recyclerviewcap.model.Message>()
    private var adapter = MessageAdapter(messages, this::onMessageItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun onMessageItemClick(message: Message) {
        val s = "${message.title}\n${message.text}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        val rv:RecyclerView = findViewById(R.id.recyclerViewMessages)
        rv.adapter = adapter

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(position == 0) 2 else 1
            }
        }
        rv.layoutManager = layoutManager
    }
}