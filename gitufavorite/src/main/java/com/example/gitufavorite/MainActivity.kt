package com.example.gitufavorite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitufavorite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Add The Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter(FavoriteAdapter.ViewType.HOME) {
            //intent for click list data
            Toast.makeText(this, "This is your favorite :)", Toast.LENGTH_LONG).show()
        }
        adapter.notifyDataSetChanged()
        binding.rvFavorite.adapter = adapter

        //setfavoriteuser
        viewModel.setFavoriteUser(this)

        //getfavoriteuser
        viewModel.getFavoriteUser()?.observe(this, Observer { users ->
            if (users != null) {
                val list = if (users is ArrayList) users else ArrayList(users)
                adapter.setList(list)
            }
        })
    }
}