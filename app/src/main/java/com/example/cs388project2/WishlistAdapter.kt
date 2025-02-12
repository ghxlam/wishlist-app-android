package com.example.cs388project2

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(private val entries: MutableList<WishlistEntry>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView
        val priceTextView: TextView
        val urlTextView: TextView
        init {
            itemNameTextView = itemView.findViewById(R.id.itemName)
            priceTextView = itemView.findViewById(R.id.itemPrice)
            urlTextView = itemView.findViewById(R.id.itemURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.wishlist_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wishlistEntry = entries[position]
        holder.itemNameTextView.text = wishlistEntry.itemName
        holder.priceTextView.text = "$${wishlistEntry.price}"
        holder.urlTextView.text = wishlistEntry.url

        holder.itemView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wishlistEntry.url))
                ContextCompat.startActivity(it.context, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(it.context, "Invalid URL for ${wishlistEntry.itemName}", Toast.LENGTH_LONG).show()
            }
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to remove ${wishlistEntry.itemName}?")
                .setPositiveButton("Delete") { _, _ ->
                    removeItem(position)
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }
    }

    fun removeItem(position: Int) {
        entries.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: WishlistEntry) {
        entries.add(item)
        notifyItemInserted(entries.size - 1)
    }
}