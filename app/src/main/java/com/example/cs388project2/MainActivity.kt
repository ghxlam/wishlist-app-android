package com.example.cs388project2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var entries: MutableList<WishlistEntry>
    lateinit var wishlistAdapter: WishlistAdapter
    lateinit var inputItemName: EditText
    lateinit var inputItemPrice: EditText
    lateinit var inputItemURL: EditText
    lateinit var btnAddItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val entriesRV = findViewById<RecyclerView>(R.id.itemList)
        inputItemName = findViewById(R.id.inputItemName)
        inputItemPrice = findViewById(R.id.inputItemPrice)
        inputItemURL = findViewById(R.id.inputItemURL)
        btnAddItem = findViewById(R.id.btnAddItem)

        entries = mutableListOf()
        wishlistAdapter = WishlistAdapter(entries)

        entriesRV.layoutManager = LinearLayoutManager(this)
        entriesRV.adapter = wishlistAdapter

        btnAddItem.setOnClickListener {
            val itemName = inputItemName.text.toString()
            val itemPrice = inputItemPrice.text.toString()
            val itemURL = inputItemURL.text.toString()

            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemURL.isNotEmpty()) {
                val newEntry = WishlistEntry(itemName, itemPrice, itemURL)
                entries.add(newEntry)
                wishlistAdapter.notifyItemInserted(entries.size - 1)
                clearInputFields(inputItemName, inputItemPrice, inputItemURL)
            }
        }
    }

    private fun clearInputFields(itemName: EditText, itemPrice: EditText, itemURL: EditText) {
        itemName.text.clear()
        itemPrice.text.clear()
        itemURL.text.clear()
    }
}