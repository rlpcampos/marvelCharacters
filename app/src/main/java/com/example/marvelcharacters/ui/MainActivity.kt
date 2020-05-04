package com.example.marvelcharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val recycleListView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val characterAdapter =
        CharacterAdapter()

    private val viewModel: CharactersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchCharactersList()
        recycleListView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = characterAdapter
        }

        viewModel.characterList.observe(this, Observer {
            if (characterAdapter.itemCount == 0)
                characterAdapter.setList(it.dataContainer.characters.map { character -> character.name })
            else
                characterAdapter.addList(it.dataContainer.characters.map { character -> character.name })
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        recycleListView.adapter = null
    }
}
