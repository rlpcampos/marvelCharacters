package com.example.marvelcharacters.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.models.Character
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListFragment : Fragment(R.layout.fragment_character_list) {

    private val viewModel: CharactersViewModel by viewModel()

    private lateinit var recycleListView: RecyclerView
    private val characterAdapter = CharacterAdapter(
        { viewModel.fetchCharactersList() },
        { character -> this.onItemClick(character) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleListView = view.findViewById<RecyclerView>(R.id.recycler_view)
        viewModel.fetchCharactersList(true)
        observableData()
        setupView()
    }

    private fun setupView() {
        recycleListView.apply {
            layoutManager =
                GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = characterAdapter
        }
    }

    private fun observableData() {
        viewModel.characterList.observe(viewLifecycleOwner, Observer {
            characterAdapter.updateCharacters(it, viewModel.hasNextPage)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { msg ->
            characterAdapter.showRetry()
            view?.also { view ->
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                    .setTextColor(Color.YELLOW)
                    .setActionTextColor(Color.GREEN)
                    .setBackgroundTint(Color.BLUE)
                    .setAction(R.string.label_retry) { viewModel.fetchCharactersList() }
                    .show()
            }
        })
    }

    private fun onItemClick(character: Character) {
        findNavController().navigate(CharacterListFragmentDirections.actionListToDetail(character))
    }

    override fun onDestroy() {
        super.onDestroy()
        recycleListView.adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        menu.findItem(R.id.action_search).actionView.run {
            this as SearchView

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.filterByName(query)
                    characterAdapter.clearData()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            setOnCloseListener {
                viewModel.clearFilter()
                characterAdapter.clearData()
                activity?.invalidateOptionsMenu()
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(windowToken, 0)
                return@setOnCloseListener true
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}