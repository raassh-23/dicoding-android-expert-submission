package com.raassh.myfavmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raassh.myfavmovies.databinding.ActivityMainBinding
import com.raassh.myfavmovies.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}