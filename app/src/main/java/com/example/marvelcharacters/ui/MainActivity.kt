package com.example.marvelcharacters.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.marvelcharacters.R
import com.example.marvelcharacters.ext.setVisibility
import com.example.marvelcharacters.network.ConnectHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val internetAlert by lazy { findViewById<ViewGroup>(R.id.internet_alert) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val appBarConfiguration = AppBarConfiguration(setOf())
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observableData()
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.character_list -> {
                    navController.navigate(R.id.character_list)
                    true
                }
                R.id.favorites_list -> {
                    navController.navigate(R.id.favorites_list)
                    true
                }
                else -> false
            }
        }

        supportActionBar?.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.character_list -> {
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
            R.id.character_list -> {
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
