package com.example.android.themoviesapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.themoviesapp.databinding.ItemDrawerHeaderBinding
import com.example.android.themoviesapp.databinding.ItemDrawerRowBinding
import com.example.android.themoviesapp.presentation.models.DrawerItem

class DrawerAdapter(
    private val items: List<DrawerItem>,
    private val onItemClick: (DrawerItem.MenuItem) -> Unit = {}): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DrawerItem.Header -> TYPE_HEADER
            is DrawerItem.MenuItem -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            val binding = ItemDrawerHeaderBinding.inflate(inflater, parent, false)
            DrawerHeaderViewHolder(binding)
        } else {
            val binding = ItemDrawerRowBinding.inflate(inflater, parent, false)
            DrawerItemViewHolder(binding, onItemClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is DrawerItemViewHolder -> {
                if (item is DrawerItem.MenuItem) {
                    holder.bindData(item)
                }
            }
            is DrawerHeaderViewHolder -> {
                // You can access header views here via holder.binding
                // e.g., holder.binding.ivProfile.setImageResource(...)
            }
            }
    }

    override fun getItemCount() = items.size

    class DrawerHeaderViewHolder(private val binding: ItemDrawerHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData() {
        }
    }
    class DrawerItemViewHolder(private val binding: ItemDrawerRowBinding,
                               private val onItemClick: (DrawerItem.MenuItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: DrawerItem.MenuItem) {
            binding.drawerMenuItem.text = itemView.context.getString(item.titleRes)
            binding.drawerMenuItem.setOnClickListener { 
                onItemClick(item)
            }
        }


    }
}