package com.example.marvelcharacters.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.marvelcharacters.R
import com.example.marvelcharacters.ext.setVisibility
import com.example.marvelcharacters.network.ConnectHandler

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val internetAlert by lazy { findViewById<ViewGroup>(R.id.internet_alert) }
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val appBarConfiguration = AppBarConfiguration(setOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observableData()
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        supportActionBar?.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.listCharacterFragment -> {
                        setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
                    }
                    else -> {
                        setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
                    }
                }
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return when (navController.currentDestination?.id) {
            R.id.listCharacterFragment -> {
                finish()
                true
            }
            else -> {
                navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
            }
        }
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    private fun observableData() {
        ConnectHandler.connectivityLiveData.observe(this, Observer {
            internetAlert.setVisibility(!it)
        })
    }
}
