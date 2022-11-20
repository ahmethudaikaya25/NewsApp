package com.androiddevs.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.data.model.Article
import com.androiddevs.mvvmnewsapp.data.model.Source
import com.androiddevs.mvvmnewsapp.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setupWithNavController(binding.flFragment[0].findNavController())
     }
}
