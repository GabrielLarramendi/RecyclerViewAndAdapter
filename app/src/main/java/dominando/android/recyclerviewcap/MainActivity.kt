package dominando.android.recyclerviewcap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import dominando.android.recyclerviewcap.adapters.MessageAdapter
import dominando.android.recyclerviewcap.model.Message

class MainActivity : AppCompatActivity() {

    private var messages = mutableListOf<dominando.android.recyclerviewcap.model.Message>()
    private var adapter = MessageAdapter(messages, this::onMessageItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lastCustomNonConfigurationInstance.let { savedMessages -> if(savedMessages is MutableList<*>) {messages.addAll(savedMessages.filterIsInstance(Message::class.java))} }

        initRecyclerView()

        val addButton = findViewById<FloatingActionButton>(R.id.floatingActionButtonAdd)
        addButton.setOnClickListener {
            addMessage()
        }
    }

    private fun onMessageItemClick(message: Message) {
        val s = "${message.title}\n${message.text}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {

        val rv:RecyclerView = findViewById(R.id.recyclerViewMessages)
        rv.adapter = adapter

        deleteWithSwipeGesture()

        val layoutManager = GridLayoutManager(this, 1)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return if(position == 0) 2 else 1
//            }
//        }
        rv.layoutManager = layoutManager
    }

    private fun addMessage() {
        val editTitle:TextInputEditText = findViewById(R.id.editTextEditTitle)
        val editText:TextInputEditText = findViewById(R.id.editTextEditText)

        val message = Message(editTitle.text.toString().trim(),
                              editText.text.toString().trim())

        if(message.text.isEmpty() || message.title.isEmpty()) {
            Toast.makeText(this, "Preencha os 2 campos!", Toast.LENGTH_SHORT).show()
        }
        if(message.text.isBlank() || message.title.isBlank()) {
            Toast.makeText(this, "Os campos nao podem ficar em branco!", Toast.LENGTH_SHORT).show()
        }

        else {
            messages.add(message)
            adapter.notifyItemInserted(messages.lastIndex)

            editTitle.text?.clear()
            editText.text?.clear()
            editTitle.requestFocus()
        }
    }

    private fun deleteWithSwipeGesture() {

        val swipe = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                messages.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        val rv: RecyclerView = findViewById(R.id.recyclerViewMessages)
        itemTouchHelper.attachToRecyclerView(rv)
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): Any {
        return messages
    }


}

