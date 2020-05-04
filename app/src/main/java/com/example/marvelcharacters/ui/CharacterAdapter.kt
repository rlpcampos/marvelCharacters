package com.example.marvelcharacters.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.Holder>() {

    private var list: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_character))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    fun addListItem(itemList: String) {
        list.add(itemList)
        notifyItemChanged(list.lastIndex)
    }

    fun addList(list: List<String>) {
        val firstIndex = this.list.lastIndex + 1
        val listSize = list.size
        this.list.addAll(list)
        notifyItemRangeInserted(firstIndex, listSize)
    }

    fun setList(list: List<String>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }


    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context)
            .inflate(layoutRes, this, attachToRoot)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            itemView.findViewById<TextView>(R.id.rotulo).text = item
        }
    }
}