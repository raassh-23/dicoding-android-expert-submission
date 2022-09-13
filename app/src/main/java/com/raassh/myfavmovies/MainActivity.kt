package com.raassh.myfavmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.raassh.myfavmovies.databinding.ActivityMainBinding
import com.raassh.myfavmovies.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(
            this,
            navHost.navController
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!(navHost.navController.navigateUp() || super.onSupportNavigateUp())) {
            onBackPressedDispatcher.onBackPressed()
        }

        return true
    }
}