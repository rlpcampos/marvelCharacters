package com.example.marvelcharacters.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.ext.setVisibility
import com.example.marvelcharacters.network.ConnectHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: CharactersViewModel by viewModel()

    private val recycleListView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val characterAdapter = CharacterAdapter { viewModel.fetchCharactersList() }
    private val internetAlert by lazy { findViewById<ViewGroup>(R.id.internet_alert) }

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

        viewModel.error.observe(this, Observer {

        })
        ConnectHandler.connectivityLiveData.observe(this, Observer {
            internetAlert.setVisibility(!it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        recycleListView.adapter = null
    }
}
