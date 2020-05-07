package com.example.marvelcharacters.ui

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marvelcharacters.R
import com.example.marvelcharacters.ext.inflate
import com.example.marvelcharacters.models.Character
import com.example.marvelcharacters.models.Thumbnail
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize

class CharacterAdapter(
    private val requestNextPage: () -> Unit,
    val onItemClick: (Character) -> Unit,
    val addFavorite: (Character) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    private val characters = mutableListOf<Character>()

    init {
        characters.add(CharacterLoading)
    }

    private fun hideLoading() {
        characters.removeAt(characters.size - 1)
        notifyItemRemoved(characters.size)
    }

    fun showRetry() {
        if (characters.isNotEmpty()) hideLoading()
        characters.add(CharacterRetry)
    }

    fun clearData() {
        characters.clear()
        notifyDataSetChanged()
    }

    fun updateCharacters(newCharacters: List<Character>, hasNextPage: Boolean) {
        val initialIndex = characters.size

        if (characters.isNotEmpty()) hideLoading()

        if (newCharacters.isEmpty() && characters.size == 0) {
            characters.add(CharacterEmpty)
        } else {
            characters.addAll(newCharacters)

            if (hasNextPage) characters.add(CharacterLoading)
        }
        notifyItemRangeInserted(initialIndex, characters.size - initialIndex)
    }

    override fun getItemViewType(position: Int): Int = characters[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> LoadingViewHolder(parent.inflate(R.layout.item_loading))
            VIEW_TYPE_EMPTY -> EmptyViewHolder(parent.inflate(R.layout.item_empyt_list))
            VIEW_TYPE_RETRY -> RetryViewHolder(parent.inflate(R.layout.item_retry))
            else -> CharacterHolder(parent.inflate(R.layout.item_character))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHolder -> requestNextPage()
            is RetryViewHolder -> holder.bind(requestNextPage)
            is EmptyViewHolder -> holder.bind(requestNextPage)
            is CharacterHolder -> holder.bind(characters[position], onItemClick, addFavorite)
            else -> throw IllegalArgumentException("Unknown ViewHolder")
        }
    }

    override fun getItemCount() = characters.size

    companion object {
        const val VIEW_TYPE_RETRY = 1
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_EMPTY = -1
    }
}

class CharacterHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(item: Character, onItemClick: (Character) -> Unit, addFavorite: (Character) -> Unit) {
        itemView.findViewById<AppCompatTextView>(R.id.text_view_title).text = item.name
        itemView.findViewById<AppCompatTextView>(R.id.text_view_description).text = item.description
        itemView.findViewById<AppCompatImageView>(R.id.image_favorite).apply {
            if (item.isFavorite)
                DrawableCompat.setTint(this.drawable, context.getColor(R.color.yellow))
            else
                DrawableCompat.setTint(this.drawable, context.getColor(R.color.black))

            setOnClickListener {
                addFavorite.invoke(item)
                DrawableCompat.setTint(this.drawable, context.getColor(R.color.yellow))
            }
        }
        itemView.findViewById<AppCompatImageView>(R.id.image_character).apply {
            Picasso.get()
                .load(
                    "${item.thumbnail.path.replace(
                        "http://",
                        "https://"
                    )}.${item.thumbnail.extension}"
                )
                .resize(100, 100)
                .centerInside()
                .placeholder(R.drawable.ic_progress_animation)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .into(this)
        }
        itemView.setOnClickListener { onItemClick.invoke(item) }
    }
}

class LoadingViewHolder(itemView: View) : ViewHolder(itemView)
class EmptyViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(retry: () -> Unit) {
        itemView.findViewById<Button>(R.id.button_retry).setOnClickListener {
            retry.invoke()
        }
    }
}

class RetryViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(retry: () -> Unit) {
        itemView.findViewById<Button>(R.id.button_retry).setOnClickListener {
            retry.invoke()
        }
    }
}

@Parcelize
object CharacterLoading : Character(
    CharacterAdapter.VIEW_TYPE_LOADING, Thumbnail("", ""), "", "", "", false, ""
)

@Parcelize
object CharacterEmpty : Character(
    CharacterAdapter.VIEW_TYPE_EMPTY, Thumbnail("", ""), "", "", "", false, ""
)

@Parcelize
object CharacterRetry : Character(
    CharacterAdapter.VIEW_TYPE_RETRY, Thumbnail("", ""), "", "", "", false, ""
)
