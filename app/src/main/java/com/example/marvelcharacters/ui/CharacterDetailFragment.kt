package com.example.marvelcharacters.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.marvelcharacters.R
import com.squareup.picasso.Picasso

class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    val args: CharacterDetailFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.character.also { item ->
            view.findViewById<AppCompatTextView>(R.id.text_view_title).text = item.name
            view.findViewById<AppCompatTextView>(R.id.text_view_description).text = item.description
            view.findViewById<AppCompatImageView>(R.id.image_character).apply {
                Picasso.get()
                    .load(
                        "${item.thumbnail.path.replace(
                            "http://",
                            "https://"
                        )}.${item.thumbnail.extension}"
                    )
                    .placeholder(R.drawable.ic_progress_animation)
                    .error(R.drawable.ic_cloud_off_black_24dp)
                    .into(this)
            }
        }

    }
}