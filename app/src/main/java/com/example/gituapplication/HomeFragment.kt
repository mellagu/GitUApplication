package com.example.gituapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gituapplication.adapter.GitUHomeAdapter
import com.example.gituapplication.databinding.FragmentHomeBinding
import com.example.gituapplication.model.UserViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewModel: UserViewModel
    private lateinit var adapter: GitUHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)

        //Call the functions
        showRecycleCard()
        makeSearchBar()
        observeMainViewModel()

    }

    //get data from model
    private fun observeMainViewModel() {
        mainViewModel.getSearchUsers().observe(viewLifecycleOwner, Observer { users ->
            Log.d("Home Fragment", users[0].login)
            if (users != null) {
                adapter.setList(users)
                showLoading(false)
            }

        })
    }

    //Make a search bar
    private fun makeSearchBar() {
        //Show data in home before search
        if (mainViewModel.listUser.value.isNullOrEmpty()) {
            mainViewModel.setSearchUser("mu")
        }
        //Make a bar
        binding.apply {
            Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("Search bar", newText ?: "")
                    showLoading(true)
                    if (!newText.isNullOrEmpty()) {
                        mainViewModel.setSearchUser(newText)
                    } else {
                        mainViewModel.setSearchUser("me")
                    }
                    return true
                }
            })
        }

    }

    //Show the progress bar while load
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun showRecycleCard() {
        binding.rvGitU.layoutManager = GridLayoutManager(activity, 2)
        adapter = GitUHomeAdapter(GitUHomeAdapter.ViewType.HOME) {
            //intent for click data card
            val intent = Intent(requireContext(), DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, it)
            startActivity(intent)
        }
        binding.rvGitU.adapter = adapter

    }
}