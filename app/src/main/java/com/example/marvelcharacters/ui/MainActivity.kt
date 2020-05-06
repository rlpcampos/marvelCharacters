package com.example.marvelcharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: CharactersViewModel by viewModel()

    private val recycleListView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val characterAdapter = CharacterAdapter { viewModel.fetchCharactersList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchCharactersList()
        recycleListView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
            adapter = characterAdapter
        }
        observableData()
    }

    private fun observableData() {

        viewModel.characterList.observe(this, Observer {
            characterAdapter.updateCharacters(it, viewModel.hasNextPage)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        recycleListView.adapter = null
    }
}
